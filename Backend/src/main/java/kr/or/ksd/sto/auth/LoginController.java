package kr.or.ksd.sto.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kr.or.ksd.sto.cmns.security.JwtTokenProvider;
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

//	@PostMapping("/login")
//	public String login(@RequestParam String userid, @RequestParam String password) {
//		String token = jwtTokenProvider.createToken(userid, password);
//		return token;
//	}

	@PostMapping("/login")
	public String login(@RequestBody LoginReqDto loginRequest) {
		log.info("test= "+ loginRequest);
		String token = jwtTokenProvider.createToken(loginRequest.getUserid(), loginRequest.getPassword());
		return token;
	}
}
