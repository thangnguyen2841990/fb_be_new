package com.example.demo.controller;

import com.example.demo.model.dto.NotificationFriendExist;
import com.example.demo.model.entity.FriendNotification;
import com.example.demo.model.entity.ListFriend;
import com.example.demo.model.entity.Notification;
import com.example.demo.model.entity.User;
import com.example.demo.service.listFriend.IListFriendService;
import com.example.demo.service.listFriend.ListFriendService;
import com.example.demo.service.notification.INotificationService;
import com.example.demo.service.notificationFriend.INotificationFriendService;
import com.example.demo.service.user.IUSerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/notification-friends")
public class FriendNotificationRestController {
    @Autowired
    private INotificationFriendService notificationFriendService;
    @Autowired
    private IUSerService userService;

    @Autowired
    private IListFriendService ListFriendService;

    @Autowired
    private INotificationService notificationService;

    @GetMapping("/allNotificationOfUser/toUserId/{toUserId}")
    public ResponseEntity<List<FriendNotification>> findAllNotificationFriendsOfUser(@PathVariable Long toUserId) {
        List<FriendNotification> friendNotificationList = this.notificationFriendService.listNotificationFriends(toUserId);
        return new ResponseEntity<>(friendNotificationList, HttpStatus.OK);
    }
    @GetMapping("/allNotificationFromUser/fromUserId/{fromUserId}")
    public ResponseEntity<List<FriendNotification>> findAllNotificationFriendsFromUser(@PathVariable Long fromUserId) {
        List<FriendNotification> friendNotificationList = this.notificationFriendService.listNotificationFriendsFromUser(fromUserId);
        return new ResponseEntity<>(friendNotificationList, HttpStatus.OK);
    }

    @PostMapping("/createNewNotificationFriend/fromUser/{fromUserId}/toUser/{toUserId}")
    public ResponseEntity<FriendNotification> createNewNotificationFriend(@PathVariable Long fromUserId, @PathVariable Long toUserId) {
        Optional<FriendNotification> friendNotificationOptional = this.notificationFriendService.findFriendNotificationByFromUserAndToUser(fromUserId, toUserId);
        FriendNotification newFriendNotification = new FriendNotification();
        if (!friendNotificationOptional.isPresent()) {
            Optional<User> fromUser = this.userService.findById(fromUserId);
            Optional<User> toUser = this.userService.findById(toUserId);
            newFriendNotification.setFromUser(fromUser.get());
            newFriendNotification.setToUser(toUser.get());
            this.notificationFriendService.save(newFriendNotification);
            Notification newNotification = new Notification();
            newNotification.setFromUser(fromUser.get());
            newNotification.setToUser(toUser.get());
            newNotification.setStatus(4);
            newNotification.setDateCreated(new Date());
            newNotification.setContent(toUser.get().getFullName() + " gửi yêu cầu kết bạn!");
            this.notificationService.save(newNotification);
        }
        return new ResponseEntity<>(newFriendNotification, HttpStatus.CREATED);
    }
    @PostMapping("/accept/notification/{notificationId}")
    public ResponseEntity<FriendNotification> accept(@PathVariable Long notificationId) {
        Optional<FriendNotification> friendNotificationOptional = this.notificationFriendService.findById(notificationId);
        if (!friendNotificationOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ListFriend newFriend = new ListFriend();
        newFriend.setUser(friendNotificationOptional.get().getToUser());
        newFriend.setFriendOfUser(friendNotificationOptional.get().getFromUser());
        this.ListFriendService.save(newFriend);
        ListFriend newFriend1 = new ListFriend();
        newFriend1.setUser(friendNotificationOptional.get().getFromUser());
        newFriend1.setFriendOfUser(friendNotificationOptional.get().getToUser());
        this.ListFriendService.save(newFriend1);
        this.notificationFriendService.remove(notificationId);
        return new ResponseEntity<>(friendNotificationOptional.get(), HttpStatus.OK);
    }
    @DeleteMapping("/refuse/notification/{notificationId}")
    public ResponseEntity<FriendNotification> refuse(@PathVariable Long notificationId) {
        Optional<FriendNotification> friendNotificationOptional = this.notificationFriendService.findById(notificationId);
        if (!friendNotificationOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.notificationFriendService.remove(notificationId);
        return new ResponseEntity<>(friendNotificationOptional.get(), HttpStatus.OK);
    }
    @PostMapping("/checkExist/fromUser/{fromUserId}/toUser/{toUserId}")
    public ResponseEntity<NotificationFriendExist> checkExist(@PathVariable Long fromUserId, @PathVariable Long toUserId) {
        Optional<FriendNotification> friendNotificationOptional = this.notificationFriendService.findFriendNotificationByFromUserAndToUser(fromUserId, toUserId);
        NotificationFriendExist notificationFriendExist = new NotificationFriendExist();
        if (!friendNotificationOptional.isPresent()) {
            notificationFriendExist.setStatus(false);
        }else {
            notificationFriendExist.setStatus(true);
        }
        return new ResponseEntity<>(notificationFriendExist, HttpStatus.OK);
    }
    @DeleteMapping("/delete/fromUser/{fromUserId}/toUser/{toUserId}")
    public ResponseEntity<FriendNotification> delete(@PathVariable Long fromUserId, @PathVariable Long toUserId) {
        Optional<FriendNotification> friendNotificationOptional = this.notificationFriendService.findFriendNotificationByFromUserAndToUser(fromUserId, toUserId);
        if (!friendNotificationOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.notificationFriendService.remove(friendNotificationOptional.get().getId());
        return new ResponseEntity<>(friendNotificationOptional.get(), HttpStatus.OK);
    }
}
