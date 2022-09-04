package com.example.demo.controller;
import com.example.demo.model.dto.GroupRequest;
import com.example.demo.model.entity.Group;
import com.example.demo.model.entity.User;
import com.example.demo.service.group.IGroupService;
import com.example.demo.service.user.IUSerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/groups")
public class GroupRestController {
    @Autowired
    private IGroupService groupService;
    @Autowired
    private IUSerService userService;
    @GetMapping("/allGroupOfUser/user/{userId}")
    public ResponseEntity<List<Group>> getAllGroupOfUser(@PathVariable Long userId) {
        Optional<User> userOptional = this.userService.findById(userId);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Group> groups = this.groupService.findAllGroupOfUser(userId);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }
    @GetMapping("/allGroupOtherUser/user/{userId}")
    public ResponseEntity<List<Group>> getAllGroupOtherUser(@PathVariable Long userId) {
        Optional<User> userOptional = this.userService.findById(userId);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Group> groups = this.groupService.findAllGroupOtherUser(userId);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }
    @GetMapping("/group/{groupId}")
    public ResponseEntity<Group> findGroupById(@PathVariable Long groupId) {
        Optional<Group> groupOptional = this.groupService.findById(groupId);
        if (!groupOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(groupOptional.get(), HttpStatus.OK);
    }
    @PostMapping("/createGroup/admin/{adminId}")
    public ResponseEntity<Group> createNewGroup(@PathVariable Long adminId, @RequestBody GroupRequest group) {
        Optional<User> userOptional = this.userService.findById(adminId);
        Group newGroup = new Group();
        newGroup.setAdmin(userOptional.get());
        newGroup.setGroupStatus(group.getGroupStatus());
        newGroup.setName(group.getName());
        newGroup.setAvatar("avatar-trang-den-cuc-chat-lu.jpg");
        newGroup.setBackGround("facbook-avatar-1.jpg");
        this.groupService.save(newGroup);
        return new ResponseEntity<>(newGroup, HttpStatus.OK);
    }
    @PutMapping("update/group/{groupId}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long groupId, @RequestBody GroupRequest group) {
        Optional<Group> groupOptional = this.groupService.findById(groupId);
        if (!groupOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        groupOptional.get().setName(group.getName());
        groupOptional.get().setGroupStatus(group.getGroupStatus());
        this.groupService.save(groupOptional.get());
        return new ResponseEntity<>(groupOptional.get(), HttpStatus.OK);
    }
    @DeleteMapping("delete/group/{groupId}")
    public ResponseEntity<Group> deleteGroup(@PathVariable Long groupId) {
        Optional<Group> groupOptional = this.groupService.findById(groupId);
        if (!groupOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.groupService.remove(groupId);
        return new ResponseEntity<>(groupOptional.get(), HttpStatus.OK);
    }
}
