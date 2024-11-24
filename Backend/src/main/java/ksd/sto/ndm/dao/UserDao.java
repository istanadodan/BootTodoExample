package ksd.sto.ndm.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
	Optional<UserVO> findByUserId(String userId);
}
