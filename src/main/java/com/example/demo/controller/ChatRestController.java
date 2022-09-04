package com.example.demo.controller;

import com.example.demo.model.dto.ChatDTO;
import com.example.demo.model.entity.Chat;
import com.example.demo.model.entity.User;
import com.example.demo.service.chat.IChatService;
import com.example.demo.service.post.IPostService;
import com.example.demo.service.user.IUSerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/chats")
public class ChatRestController {
    @Autowired
    private IChatService chatService;
    @Autowired
    private IUSerService userService;

    @Autowired
    private IPostService postService;
    @GetMapping
    public ResponseEntity<List<ChatDTO>> getAllChat(@RequestParam("userId1") Long userId1,
                                                    @RequestParam("userId2") Long userId2
    ) {
        List<Chat> listChat = chatService.listChatOfMe(userId1, userId2);
        List<ChatDTO> chats = new ArrayList<>();
        for (int i = 0; i < listChat.size(); i++) {
            chats.add(new ChatDTO(listChat.get(i).getId(),
                    listChat.get(i).getSender(),
                    listChat.get(i).getReceiver(),
                    listChat.get(i).getContent(),
                    listChat.get(i).isStatus(),
                    postService.getDiffDays(listChat.get(i).getTime(), new Date()) ));
        }
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Chat> createNewChat(@RequestBody Chat chat) {
        long milis = System.currentTimeMillis();
        Date date = new Date();
        chat.setTime(date);
        chatService.save(chat);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChat(@PathVariable Long id) {
        Optional<Chat> categoryOptional = chatService.findById(id);
        return categoryOptional.map(chat -> new ResponseEntity<>(chat, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chat> updateChat(@PathVariable Long id, @RequestBody Chat chat) {
        Optional<Chat> categoryOptional = chatService.findById(id);
        return categoryOptional.map(chat1 -> {
            chat.setId(chat1.getId());
            return new ResponseEntity<>(chatService.save(chat), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id1}/{id2}")
    public ResponseEntity<Chat> deleteChat(@PathVariable Long id1, @PathVariable Long id2) {
        User user1 = this.userService.findById(id1).get();
        User user2 = this.userService.findById(id2).get();
        List<Chat> listChat = this.chatService.listChatOfMe(user1.getId(), user2.getId());
        for (int i = 0; i < listChat.size(); i++) {
            this.chatService.remove(listChat.get(i).getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
