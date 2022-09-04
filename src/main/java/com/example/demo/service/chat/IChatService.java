package com.example.demo.service.chat;

import com.example.demo.model.entity.Chat;

import java.util.List;
import java.util.Optional;

public interface IChatService {

    Chat save(Chat chat);

    void remove(Long id);

    Optional<Chat> findById(Long id);

    List<Chat> listChatOfMe(Long senderId, Long receiverId);

}
