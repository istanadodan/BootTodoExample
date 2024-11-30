package ksd.sto.ndm.infs;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ksd.sto.ndm.domain.dto.BoardDTO;

@Mapper
public interface BoardDao {
	List<BoardDTO> getAllBoardList();
}
