package ksd.sto.ndm.domain.service;

import java.util.List;

import ksd.sto.ndm.domain.dto.UserDTO;

public interface UserService {

    public void createUser(UserDTO userDTO);
    public List<UserDTO> getListUser();
    public UserDTO getUserById(String userId);
}