package ksd.sto.ndm.domain.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ksd.sto.ndm.cmns.security.JwtTokenProvider;
import ksd.sto.ndm.domain.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;

    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/example")
    public ResponseEntity<String> exampleEndpoint() {
        // API 로직
        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/login")
    @Operation(description = "로그인처리")
    public ResponseEntity<String> login(@RequestParam("userId") String userid,
            @RequestParam("password") String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증 성공 시 JWT 생성
        if (authentication != null && authentication.isAuthenticated()) {
            SimpleGrantedAuthority admin_role = new SimpleGrantedAuthority("ROLE_ADMIN");
            String jwt = jwtTokenProvider
                .createToken(authentication.getName(), List.of(admin_role));
            return ResponseEntity.ok(jwt); // 응답 객체에 토큰 포함
        }

        // 인증 실패 시 401 반환
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }

    @PostMapping("/login2")
    @Operation(summary = "로그인처리", description = "로그인처리")
    public ResponseEntity<?> login2() {
        Authentication auth_info = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth_info:{}".formatted(auth_info.getName()));
        // 인증 성공 시 JWT 생성
        if (auth_info != null && auth_info.isAuthenticated()) {
            String jwt = jwtTokenProvider
                .createToken(auth_info.getName(), auth_info.getAuthorities());
            return ResponseEntity.ok(jwt); // 응답 객체에 토큰 포함
        }

        // 인증 실패 시 401 반환
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }

}
