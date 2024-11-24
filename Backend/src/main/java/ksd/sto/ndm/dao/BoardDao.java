package ksd.sto.ndm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ksd.sto.ndm.dto.Board;

@Mapper
public interface BoardDao {
	List<Board> getAllBoardList();
}
