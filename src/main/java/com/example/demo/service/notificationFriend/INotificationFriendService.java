package com.example.demo.service.notificationFriend;

import com.example.demo.model.entity.FriendNotification;

import java.util.List;
import java.util.Optional;

public interface INotificationFriendService {
    FriendNotification save(FriendNotification friendNotification);

    Optional<FriendNotification> findFriendNotificationByFromUserAndToUser(Long fromUseId, Long toUserId);

    List<FriendNotification> listNotificationFriends(Long toUserId);

    Optional<FriendNotification> findById(Long id);

    void remove(Long id);

    List<FriendNotification> listNotificationFriendsFromUser(Long fromUserId);


}
