package ksd.sto.ndm.domain.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ksd.sto.ndm.cmns.security.JwtTokenProvider;
import ksd.sto.ndm.domain.dto.BoardDTO;
import ksd.sto.ndm.domain.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final JwtTokenProvider tokenProvider;

    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/example")
    public ResponseEntity<String> exampleEndpoint() {
        // API 로직
        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/list")
    public List<BoardDTO> finaAll() {
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

}
