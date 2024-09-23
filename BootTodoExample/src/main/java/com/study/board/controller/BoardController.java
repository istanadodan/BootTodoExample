package com.study.board.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.board.dto.Board;
import com.study.board.service.BoardService;


@RestController
public class BoardController {
	@Autowired
	private BoardService boardService;
	
//	public BoardController(BoardService boardService) {
//		this.boardService = boardService;
//	}
	
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
		output.add("data5");
		output.add("data6");
		output.add("data7");
		output.add("data8");
		output.add("data9");
		return output;
	}
	
}
