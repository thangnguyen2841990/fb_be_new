package com.example.demo.service.groupNotification;

import com.example.demo.model.entity.GroupNotification;
import com.example.demo.repository.IGroupNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupNotificationService implements IGroupNotificationService {
    @Autowired
    private IGroupNotificationRepository groupNotificationRepository;


    @Override
    public Optional<GroupNotification> findGroupNotificationByFromUserAndAndToUserAndGroup(Long fromUserId, Long groupId) {
        return this.groupNotificationRepository.findGroupNotificationByFromUserAndAndToUserAndGroup(fromUserId, groupId);
    }

    @Override
    public GroupNotification save(GroupNotification newGroupNotification) {
        return this.groupNotificationRepository.save(newGroupNotification);
    }

    @Override
    public List<GroupNotification> findAllGroupNotiOfUser(Long fromUserId) {
        return this.groupNotificationRepository.findAllGroupNotiOfUser(fromUserId);
    }

    @Override
    public void remove(Long notiId) {
        this.groupNotificationRepository.deleteById(notiId);
    }

    @Override
    public List<GroupNotification> findAllGroupNotiOfGroup(Long groupId) {
        return this.groupNotificationRepository.findAllGroupNotiOfGroup(groupId);
    }

    @Override
    public Optional<GroupNotification> findById(Long id) {
        return this.groupNotificationRepository.findById(id);
    }
}
