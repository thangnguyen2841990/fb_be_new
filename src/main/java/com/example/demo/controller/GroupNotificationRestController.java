package com.example.demo.controller;

import com.example.demo.model.entity.Group;
import com.example.demo.model.entity.GroupNotification;
import com.example.demo.model.entity.User;
import com.example.demo.service.group.IGroupService;
import com.example.demo.service.groupNotification.IGroupNotificationService;
import com.example.demo.service.user.IUSerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/group-notifications")
public class GroupNotificationRestController {
    @Autowired
    private IGroupNotificationService groupNotificationService;
    @Autowired
    private IUSerService userService;
    @Autowired
    private IGroupService groupService;

    @GetMapping("/allNotiOfUser/user/{userID}")
    public ResponseEntity<List<GroupNotification>> findAllNotiOfUser(@PathVariable Long userID) {
        List<GroupNotification> notifications = this.groupNotificationService.findAllGroupNotiOfUser(userID);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
    @GetMapping("/allNotiOfGroup/group/{groupid}")
    public ResponseEntity<List<GroupNotification>> findAllGroupNotiOfGroup(@PathVariable Long groupid) {
        List<GroupNotification> notifications = this.groupNotificationService.findAllGroupNotiOfGroup(groupid);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
    @PostMapping("/createNewNotification/user/{userId}/group/{groupId}")
    public ResponseEntity<GroupNotification> createNewNotification(@PathVariable Long userId, @PathVariable Long groupId) {
        Optional<User> userOptional = this.userService.findById(userId);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Group> groupOptional = this.groupService.findById(groupId);
        if (!groupOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        GroupNotification newGroupNotification = new GroupNotification();
        Optional<GroupNotification> groupNotificationOptional = this.groupNotificationService.findGroupNotificationByFromUserAndAndToUserAndGroup(userId, groupId);
        if (!groupNotificationOptional.isPresent()) {
            newGroupNotification.setFromUser(userOptional.get());
            newGroupNotification.setGroup(groupOptional.get());
            this.groupNotificationService.save(newGroupNotification);
        }
        return new ResponseEntity<>(newGroupNotification, HttpStatus.CREATED);
    }
        @DeleteMapping("/delete/user/{userId}/group/{groupId}")
        public ResponseEntity<GroupNotification> deleteGroupNotification(@PathVariable Long userId, @PathVariable Long groupId) {
            Optional<GroupNotification> groupNotificationOptional = this.groupNotificationService.findGroupNotificationByFromUserAndAndToUserAndGroup(userId, groupId);
            if (!groupNotificationOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            this.groupNotificationService.remove(groupNotificationOptional.get().getId());
            return new ResponseEntity<>(groupNotificationOptional.get(), HttpStatus.OK);
        }
        @DeleteMapping("/delete/notification/{notificationId}")
        public ResponseEntity<GroupNotification> deleteGroupNotification(@PathVariable Long notificationId) {
            Optional<GroupNotification> groupNotificationOptional = this.groupNotificationService.findById(notificationId);
            if (!groupNotificationOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            this.groupNotificationService.remove(notificationId);
            return new ResponseEntity<>(groupNotificationOptional.get(), HttpStatus.OK);
        }
}
