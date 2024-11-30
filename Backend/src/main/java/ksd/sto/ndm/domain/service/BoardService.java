package ksd.sto.ndm.domain.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ksd.sto.ndm.domain.dto.BoardDTO;
import ksd.sto.ndm.infs.BoardDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardDao boardDao;
	
	public List<BoardDTO> getAllBoardList() {
	    log.info("BoardService.getAllBoardList()");
		return  boardDao.getAllBoardList();
	}	
}
