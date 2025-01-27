package ksd.sto.ndm.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ksd.sto.ndm.domain.dto.UserDTO;
import ksd.sto.ndm.infs.UserDao;
import ksd.sto.ndm.infs.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public void createUser(UserDTO userDTO) {
        userDao.insertUser(userDTO);
        if (true) {
            // float i = 1/0;
        }
    }

    public List<UserDTO> getListUser() {
        List<UserVO> userlst = userDao.selectList();
        return userlst.stream().map(vo -> vo.toDTO()).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(String userId) {
        return userDao.selectUserBy(userId).map(vo -> vo.toDTO()).orElse(null);
    }
}
