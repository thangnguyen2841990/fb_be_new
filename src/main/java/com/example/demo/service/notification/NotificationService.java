package com.example.demo.service.notification;

import com.example.demo.model.dto.NotificationDTO;
import com.example.demo.model.entity.Notification;
import com.example.demo.repository.INotificationRepository;
import com.example.demo.service.post.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationService implements INotificationService{
    @Autowired
    private INotificationRepository notificationRepository;
    @Autowired
    private IPostService postService;

    @Override
    public List<Notification> getAllNotificationOfUser(Long userId) {
        return this.notificationRepository.getAllNotificationOfUser(userId);
    }

    @Override
    public Notification save(Notification newNotification) {
        return this.notificationRepository.save(newNotification);
    }

    @Override
    public NotificationDTO mapperNotificationEntityToDto(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setStatus(notification.getStatus());
        notificationDTO.setFromUser(notification.getFromUser());
        notificationDTO.setToUser(notification.getToUser());
        notificationDTO.setContent(notification.getContent());
        notificationDTO.setDateCreated(this.postService.getDiffDays(notification.getDateCreated(), new Date()));
        return notificationDTO;
    }

    @Override
    public List<NotificationDTO> mapperListOfNotifications(List<Notification> notificationList) {
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (int i = 0; i < notificationList.size(); i++) {
            notificationDTOList.add(mapperNotificationEntityToDto(notificationList.get(i)));
        }
        return notificationDTOList;
    }
}
