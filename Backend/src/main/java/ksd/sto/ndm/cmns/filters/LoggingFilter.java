package ksd.sto.ndm.cmns.filters;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.swagger.v3.oas.models.PathItem.HttpMethod;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // context
        Authentication context = SecurityContextHolder.getContext().getAuthentication();
        String reqUrl = request.getRequestURI();
        String clientIpAddress = request.getRemoteAddr();
        String userId = (context == null)
                ? "Not_Login"
                : ((UserDetails) context.getPrincipal()).getUsername();
        @SuppressWarnings("deprecation")
        String now = new Date().toGMTString();
        String requestBody = null;

        log
            .info("request url:{}, client Ip address:{}, userId:{}", reqUrl, clientIpAddress,
                    userId);

        if (HttpMethod.GET.name().equals(request.getMethod()) == false) {
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}