package com.example.demo.service.groupNotification;

import com.example.demo.model.entity.GroupNotification;

import java.util.List;
import java.util.Optional;

public interface IGroupNotificationService {
    Optional<GroupNotification> findGroupNotificationByFromUserAndAndToUserAndGroup(Long fromUserId, Long groupId);

    GroupNotification save(GroupNotification newGroupNotification);

    List<GroupNotification> findAllGroupNotiOfUser(Long fromUserId);

    void remove(Long notiId);

    List<GroupNotification> findAllGroupNotiOfGroup(Long groupId);

    Optional<GroupNotification> findById(Long id);

}
