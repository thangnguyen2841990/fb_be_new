package com.example.demo.controller;

import com.example.demo.model.dto.NotificationDTO;
import com.example.demo.model.entity.Notification;
import com.example.demo.service.notification.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/notifications")
public class NotificationRestController {
    @Autowired
    private INotificationService notificationService;

    @GetMapping("/allOfUser/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getAllNotificationOfUser(@PathVariable Long userId) {
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        List<Notification> notifications = this.notificationService.getAllNotificationOfUser(userId);
        notificationDTOList = this.notificationService.mapperListOfNotifications(notifications);
        return new ResponseEntity<>(notificationDTOList, HttpStatus.OK);
    }
}
