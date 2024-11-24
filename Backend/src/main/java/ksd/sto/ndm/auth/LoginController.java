package ksd.sto.ndm.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ksd.sto.ndm.cmns.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequiredArgsConstructor
@Log4j
public class LoginController {

	private final JwtTokenProvider jwtTokenProvider;

	@Operation(security = { @SecurityRequirement(name = "bearerAuth") })
	@GetMapping("/example")
	public ResponseEntity<String> exampleEndpoint() {
		// API 로직
		return ResponseEntity.ok("Hello");
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam("userId") String userid, @RequestParam("password") String password) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증 성공 시 JWT 생성
        if (authentication != null && authentication.isAuthenticated()) {
            String jwt = jwtTokenProvider.createToken(authentication.getName(), authentication.getAuthorities());
            return ResponseEntity.ok(jwt); // 응답 객체에 토큰 포함
        }

        // 인증 실패 시 401 반환
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
	}
	
    @PostMapping("/login2")
    public ResponseEntity<?> login2(Authentication auth_info) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth_info:{}".formatted(auth_info.getName()));
        // 인증 성공 시 JWT 생성
        if (authentication != null && authentication.isAuthenticated()) {
            String jwt = jwtTokenProvider.createToken(auth_info.getName(), auth_info.getAuthorities());
            return ResponseEntity.ok(jwt); // 응답 객체에 토큰 포함
        }

        // 인증 실패 시 401 반환
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }

}
