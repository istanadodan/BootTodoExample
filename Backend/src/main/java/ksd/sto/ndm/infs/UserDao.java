package ksd.sto.ndm.infs;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import ksd.sto.ndm.domain.dto.UserDTO;
import ksd.sto.ndm.infs.vo.UserVO;

@Mapper
public interface UserDao {
	Optional<UserVO> findByUserId(String userId);

    List<UserVO> selectList();

    void insertUser(UserDTO userDTO);
}