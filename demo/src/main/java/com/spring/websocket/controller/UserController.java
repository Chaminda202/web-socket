package com.spring.websocket.controller;

import com.spring.websocket.exception.UserNotFoundException;
import com.spring.websocket.model.UserDTO;
import com.spring.websocket.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid UserDTO userDTO) {
        final UserDTO response = this.userService.saveUser(userDTO);
        return ResponseEntity
                .accepted()
                .body(response);
    }

    @GetMapping(value = "{name}")
    @ResponseBody
    public ResponseEntity<UserDTO> getUserByName(@PathVariable String name) throws UserNotFoundException {
        final UserDTO response = this.userService.getUserByName(name);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        final List<UserDTO> response = this.userService.getAllUsers();
        return ResponseEntity
                .ok()
                .body(response);
    }
}
