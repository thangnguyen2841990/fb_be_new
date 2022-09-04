package com.example.demo.controller;

import com.example.demo.model.entity.Chat;
import com.example.demo.service.chat.IChatService;
import com.example.demo.service.user.IUSerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@CrossOrigin("*")
public class WebsocketRestController {
    @Autowired
    private IChatService chatService;


    @MessageMapping("/chats")
    @SendTo("/topic/chats")
    public Chat chatting(Chat chat) {
        long milis = System.currentTimeMillis();
        Date date = new Date();
        chat.setTime(date);
        chatService.save(chat);
        return chat;
    }

}
