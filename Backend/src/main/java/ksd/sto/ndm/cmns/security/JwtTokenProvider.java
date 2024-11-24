package ksd.sto.ndm.cmns.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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

	public String createToken(String username, Collection<? extends GrantedAuthority> roles) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", roles);

		long now = (new Date()).getTime();
		Date validity = new Date(now + this.tokenValidityInMilliseconds);

		return Jwts.builder()
				.claims(claims)
				.subject(username)
				.issuedAt(new Date(now))
				.expiration(validity)
				.signWith(key)
				.compact();
	}

	// JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Collection<? extends GrantedAuthority> authorities = 
                ((List<?>) claims.get("roles")).stream()
                    .map(role -> {
                        if (role instanceof String s1) {
                            return new SimpleGrantedAuthority(s1);
                        } else if (role instanceof Map s2) {
                            return new SimpleGrantedAuthority((String)s2.get("authority"));
                        }
                        return (GrantedAuthority) role;
                    })
                    .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
    
    // 토큰에서 값 추출
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // 토큰 유효성 검증
 // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            // 잘못된 JWT 서명
            throw new JwtException("Invalid JWT signature");
        } catch (ExpiredJwtException e) {
            // 만료된 JWT 토큰
            throw new JwtException("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 JWT 토큰
            throw new JwtException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            // JWT 토큰이 잘못됨
            throw new JwtException("JWT token is invalid");
        }
    }

}