package com.spring.websocket.service;

import java.util.List;

import com.spring.websocket.exception.UserNotFoundException;
import com.spring.websocket.model.UserDTO;

public interface UserService {
    UserDTO getUserByName(String name) throws UserNotFoundException;
    UserDTO saveUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
}
