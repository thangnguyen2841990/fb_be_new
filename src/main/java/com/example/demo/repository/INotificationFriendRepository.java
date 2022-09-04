package com.example.demo.repository;

import com.example.demo.model.entity.FriendNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INotificationFriendRepository extends JpaRepository<FriendNotification, Long> {
    @Query(value = "select * from friend_notifications where from_user_id in (?1, ?2) and to_user_id in (?1, ?2)", nativeQuery = true)
    Optional<FriendNotification> findFriendNotificationByFromUserAndToUser(Long fromUseId, Long toUserId);

    @Query(value = "select * from friend_notifications where to_user_id = ?1",nativeQuery = true)
    List<FriendNotification> listNotificationFriends(Long toUserId);

    @Query(value = "select * from friend_notifications where from_user_id = ?1",nativeQuery = true)
    List<FriendNotification> listNotificationFriendsFromUser(Long fromUserId);


}
