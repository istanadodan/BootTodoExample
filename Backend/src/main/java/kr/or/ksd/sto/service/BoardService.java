package kr.or.ksd.sto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ksd.sto.dao.BoardMapper;
import kr.or.ksd.sto.dto.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BoardService {

	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
	@Autowired
	private BoardMapper boardMapper;
	
	public BoardService(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}
	
	public List<Board> getAllBoardList() {
		logger.info("BoardService.getAllBoardList()");
		return  boardMapper.getAllBoardList();
	}
}
