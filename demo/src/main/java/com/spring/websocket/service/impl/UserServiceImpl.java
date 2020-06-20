package com.spring.websocket.service.impl;


import com.spring.websocket.entity.User;
import com.spring.websocket.exception.UserNotFoundException;
import com.spring.websocket.mapper.UserMapper;
import com.spring.websocket.model.UserDTO;
import com.spring.websocket.repository.UserRepository;
import com.spring.websocket.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userService")
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserDTO getUserByName(String name) throws UserNotFoundException {
        return this.userRepository
                .findByUsername(name)
                .map(this.userMapper::mapUserToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User is not exist " + name));
    }

    @Override
    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {
        Optional<User> optionalUser = this.userRepository
                .findByUsername(userDTO.getUsername());
        if(optionalUser.isPresent()) {
            return optionalUser
                    .map(this.userMapper::mapUserToUserDto)
                    .get();
        }
        return this.userMapper
                .mapUserToUserDto(this.userRepository.save(this.userMapper.mapUserDtoToUserEntity(userDTO)));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(this.userMapper::mapUserToUserDto)
                .collect(Collectors.toList());
    }
}
