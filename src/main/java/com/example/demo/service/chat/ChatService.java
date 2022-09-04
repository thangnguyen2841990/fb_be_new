package com.example.demo.service.chat;

import com.example.demo.model.entity.Chat;
import com.example.demo.repository.IChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService implements IChatService {
    @Autowired
    private IChatRepository chatRepository;

    @Override
    public Chat save(Chat chat) {
        return this.chatRepository.save(chat);
    }

    @Override
    public void remove(Long id) {
        this.chatRepository.deleteById(id);
    }

    @Override
    public Optional<Chat> findById(Long id) {
        return chatRepository.findById(id);
    }

    @Override
    public List<Chat> listChatOfMe(Long senderId, Long receiverId) {
        return chatRepository.listChatOfMe(senderId, receiverId);
    }
}
