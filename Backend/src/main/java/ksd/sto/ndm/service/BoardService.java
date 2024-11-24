package ksd.sto.ndm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ksd.sto.ndm.dao.BoardDao;
import ksd.sto.ndm.dto.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardDao boardDao;
	
	public List<Board> getAllBoardList() {
	    log.info("BoardService.getAllBoardList()");
		return  boardDao.getAllBoardList();
	}
}
