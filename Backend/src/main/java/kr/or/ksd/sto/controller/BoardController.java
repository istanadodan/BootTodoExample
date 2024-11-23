package kr.or.ksd.sto.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import kr.or.ksd.sto.cmns.security.JwtTokenProvider;
import kr.or.ksd.sto.dto.Board;
import kr.or.ksd.sto.service.BoardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<?> login(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 성공 시 JWT 생성
        if (authentication != null && authentication.isAuthenticated()) {
            String jwt = tokenProvider.createToken(authentication);
            return ResponseEntity.ok(jwt); // 응답 객체에 토큰 포함
        }

        // 인증 실패 시 401 반환
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }

    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/example")
    public ResponseEntity<String> exampleEndpoint() {
        // API 로직
        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/list")
    public List<Board> finaAll() {
        return boardService.getAllBoardList();
    }

    @GetMapping("/")
    public List<String> getData() {
        List<String> output = new ArrayList<String>();
        output.add("data1");
        output.add("data2");
        output.add("data3");
        output.add("data4");
        return output;
    }
    @PostMapping("/login")
    public String login(Authentication auth_info) {
        return auth_info.getName();
    }

}
