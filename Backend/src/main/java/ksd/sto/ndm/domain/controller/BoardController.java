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
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/list")
    public List<BoardDTO> finaAll() {
        List<BoardDTO> result = boardService.getAllBoardList();
        return result;
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
