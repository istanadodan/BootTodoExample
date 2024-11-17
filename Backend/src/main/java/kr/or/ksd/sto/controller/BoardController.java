package kr.or.ksd.sto.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kr.or.ksd.sto.dto.Board;
import kr.or.ksd.sto.service.BoardService;


@RestController
@RequestMapping("/api")
public class BoardController {
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService= boardService;
	}
	
	@Operation(security = { @SecurityRequirement(name = "bearerAuth") })
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
	
}
