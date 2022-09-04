package com.example.demo.controller;

import com.example.demo.model.dto.CheckFriend;
import com.example.demo.model.entity.ListFriend;
import com.example.demo.model.entity.User;
import com.example.demo.service.listFriend.IListFriendService;
import com.example.demo.service.user.IUSerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/listFriends")
public class ListFriendRestController {
    @Autowired
    private IListFriendService listFriendService;
    @Autowired
    private IUSerService userService;

    @GetMapping("/allFriends/user/{userId}")
    public ResponseEntity<List<ListFriend>> getAllFriendsOfUser(@PathVariable Long userId) {
        List<ListFriend> listFriends = this.listFriendService.listFriendsOfUser(userId);
        return new ResponseEntity<>(listFriends, HttpStatus.OK);
    }
    @PostMapping("/checkFriend/currentUser/{currentUserId}/user/{userId}")
    public ResponseEntity<CheckFriend> checkFriend(@PathVariable Long currentUserId, @PathVariable Long userId){
        List<ListFriend> listFriends = this.listFriendService.listFriendsOfUser(currentUserId);
        Optional<User> userOptional = this.userService.findById(userId);
        CheckFriend checkFriend = new CheckFriend();
        for (int i = 0; i < listFriends.size(); i++) {
            if (listFriends.get(i).getFriendOfUser().getUsername().equals(userOptional.get().getUsername())){
                checkFriend.setStatus(true);
            }else {
                checkFriend.setStatus(false);
            }
        }
        return new ResponseEntity<>(checkFriend, HttpStatus.OK);
    }
    @DeleteMapping("/unFriend/friend/{friendId}")
    public ResponseEntity<ListFriend> unFriend(@PathVariable Long friendId) {
        Optional<ListFriend> listFriendOptional = this.listFriendService.findById(friendId);
        if (!listFriendOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<ListFriend> listFriendOptional1 = this.listFriendService.findFriendByFromUserAndToUser(listFriendOptional.get().getFriendOfUser().getId(),
                listFriendOptional.get().getUser().getId());
        this.listFriendService.remove(listFriendOptional1.get().getId());
        this.listFriendService.remove(friendId);

        return new ResponseEntity<>(listFriendOptional.get(), HttpStatus.OK);
    }
}
