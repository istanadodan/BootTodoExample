package ksd.sto.ndm.domain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ksd.sto.ndm.cmns.ApiResponse;
import ksd.sto.ndm.cmns.contexts.ContextService;
import ksd.sto.ndm.domain.dto.UserDTO;
import ksd.sto.ndm.domain.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;
    private final ContextService contextService;

//    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/example")
    public ApiResponse<Object> exampleEndpoint() {
        // API 로직
        return ApiResponse.builder().data("OK").build();
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserDTO userDTO) throws Exception {
        userService.createUser(userDTO);
        return "OK";
    }
    @PreAuthorize("hasAnyRole('ANONYMOUS')")
    @PostMapping("/list")
    public ResponseEntity<Object> listUsers() throws Exception {
        return ResponseEntity.ok(userService.getListUser());
    }
    @GetMapping("/msg")
    public String getMessage(@RequestParam("code") String code) {
        return contextService.getMessage(code);
    }

}
