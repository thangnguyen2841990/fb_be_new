package com.example.demo.service.notificationFriend;

import com.example.demo.model.entity.FriendNotification;
import com.example.demo.repository.INotificationFriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationFriendService implements INotificationFriendService{
    @Autowired
    private INotificationFriendRepository INotificationFriendRepository;
    @Override
    public FriendNotification save(FriendNotification friendNotification) {
        return this.INotificationFriendRepository.save(friendNotification);
    }

    @Override
    public Optional<FriendNotification> findFriendNotificationByFromUserAndToUser(Long fromUseId, Long toUserId) {
        return this.INotificationFriendRepository.findFriendNotificationByFromUserAndToUser(fromUseId, toUserId);
    }

    @Override
    public List<FriendNotification> listNotificationFriends(Long toUserId) {
        return this.INotificationFriendRepository.listNotificationFriends(toUserId);
    }

    @Override
    public Optional<FriendNotification> findById(Long id) {
        return this.INotificationFriendRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        this.INotificationFriendRepository.deleteById(id);
    }

    @Override
    public List<FriendNotification> listNotificationFriendsFromUser(Long fromUserId) {
        return this.INotificationFriendRepository.listNotificationFriendsFromUser(fromUserId);
    }
}
