package com.example.demo.repository;

import com.example.demo.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChatRepository extends JpaRepository<Chat, Long> {
    @Query(value = "select * from chats where sender_id in (?1, ?2) and receiver_id in (?1, ?2)", nativeQuery = true)
    List<Chat> listChatOfMe(Long senderId, Long receiverId);
}
