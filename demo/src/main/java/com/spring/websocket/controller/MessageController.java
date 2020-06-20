package com.spring.websocket.controller;

import com.spring.websocket.model.MessageModel;
import com.spring.websocket.model.UserDTO;
import com.spring.websocket.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class MessageController {
    private UserService userService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping(value = "/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel messageModel) {
        final UserDTO userDTO = this.userService.getUserByName(to);
        if(userDTO != null) {
            this.simpMessagingTemplate.convertAndSend("/topic/message/" + to, messageModel);
        }
    }
}
