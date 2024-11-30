package ksd.sto.ndm.cmns.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // JWT 추출
        String accessToken = resolveToken(request);
        if (StringUtils.hasText(accessToken) == true) {
            try {
                /*
                 * 휴효성 체크
                 */
                Authentication authentication = tokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException e) {
                // 만료된 JWT 토큰
                throw new ServletException("토큰이 형식이 다르거나 유효하지 않음");
            }
        }

        filterChain.doFilter(request, response);
    }

    /* 토큰 추출 */
    private String resolveToken(HttpServletRequest request) {

        String tokenHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(tokenHeader) == false || tokenHeader.startsWith("Bearer ") == false)
            return null;

        return tokenHeader.substring(7);
    }

}