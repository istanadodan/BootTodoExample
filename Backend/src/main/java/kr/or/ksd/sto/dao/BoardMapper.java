package kr.or.ksd.sto.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ksd.sto.dto.Board;

@Mapper
public interface BoardMapper {
	List<Board> getAllBoardList();
}
