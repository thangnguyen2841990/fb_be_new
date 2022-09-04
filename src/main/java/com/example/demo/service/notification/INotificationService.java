package com.example.demo.service.notification;

import com.example.demo.model.dto.NotificationDTO;
import com.example.demo.model.entity.Notification;

import java.util.List;

public interface INotificationService {
    List<Notification> getAllNotificationOfUser(Long userId);

    Notification save(Notification newNotification);

    NotificationDTO mapperNotificationEntityToDto(Notification notification);

    List<NotificationDTO> mapperListOfNotifications(List<Notification> notificationList);

}
