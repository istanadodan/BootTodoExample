package ksd.sto.ndm.cmns.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long tokenValidityInMilliseconds;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    public String createToken(String userId, Collection<? extends GrantedAuthority> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles.stream().map(r->r.getAuthority()).collect(Collectors.toList()));

        Date now = new Date();
        Date expDate = new Date(now.getTime() + this.tokenValidityInMilliseconds);

        return Jwts
            .builder()
            .claims(claims)
            .subject(userId)
            .issuedAt(now)
            .expiration(expDate)
            .signWith(key)
            .compact();
    }
    /**
     * JWT 토큰에서 인증 정보 조회
        JSON 직렬화 특성    
        
        JWT는 기본적으로 JSON 형식으로 데이터를 저장합니다.
        JSON에서 배열은 타입 정보를 잃어버리고 List 형태로 저장됩니다.        
        
        타입 변환 차이점 
        String[]로 변환 시: JSON 배열을 String 배열로 직접 변환하려고 하면 실패할 수 있습니다.
        List<String>로 변환 시: JSON 배열의 기본 구조와 더 잘 호환되어 성공적으로 변환됩니다.
        
     * @param accesToken
     * @return
     * @throws JwtException
     */
    public Authentication getAuthentication(String accesToken) throws JwtException {
        // 토큰 유효성체크는 예외를 넘기는 방식으로 처리
        Claims claims = getClaimsFromToken(accesToken);
        
        @SuppressWarnings("unchecked")
        List<String> roleLst = claims.get("roles", List.class);
        
        Collection<? extends GrantedAuthority> authorities = roleLst.stream()
            .map(role -> new SimpleGrantedAuthority(role))
            .collect(Collectors.toList());


        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, accesToken, authorities);
    }

    // 토큰 유효성 검증
    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            // 유효하지 않은 토큰
            return false;
        }
    }

    private Claims getClaimsFromToken(String token) throws JwtException {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

}