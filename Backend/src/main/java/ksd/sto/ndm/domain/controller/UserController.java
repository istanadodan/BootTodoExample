package ksd.sto.ndm.domain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ksd.sto.ndm.cmns.security.JwtTokenProvider;
import ksd.sto.ndm.domain.dto.UserDTO;
import ksd.sto.ndm.domain.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserServiceImpl userService;

	@Operation(security = { @SecurityRequirement(name = "bearerAuth") })
	@GetMapping("/example")
	public ResponseEntity<String> exampleEndpoint() {
		// API 로직
		return ResponseEntity.ok("Hello");
	}
	

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) throws Exception {
        userService.createUser(userDTO);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/list")
    public ResponseEntity<Object> listUsers() throws Exception {
        return ResponseEntity.ok(userService.getListUser());
    }

}
