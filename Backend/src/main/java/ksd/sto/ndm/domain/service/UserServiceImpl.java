package ksd.sto.ndm.domain.service;

import org.springframework.stereotype.Service;

import ksd.sto.ndm.domain.dto.UserDTO;
import ksd.sto.ndm.infs.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserDao userDao;

    public void createArticle(UserDTO userDTO) {
        userDao.insertBoard(userDTO);
        if(true) {
//            float i = 1/0;            
        }
    }
}
