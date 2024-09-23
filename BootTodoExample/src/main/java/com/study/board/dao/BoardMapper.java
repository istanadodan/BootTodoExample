package com.study.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.board.dto.Board;

@Mapper
public interface BoardMapper {
	List<Board> getAllBoardList();
}
