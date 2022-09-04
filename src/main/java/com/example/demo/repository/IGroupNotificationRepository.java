package com.example.demo.repository;

import com.example.demo.model.entity.GroupNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGroupNotificationRepository extends JpaRepository<GroupNotification, Long> {
    @Query(value = "select * from group_notifications where from_user_id = ?1 and group_id = ?2", nativeQuery = true)
    Optional<GroupNotification> findGroupNotificationByFromUserAndAndToUserAndGroup(Long fromUserId, Long groupId);
    @Query(value="select * from group_notifications where from_user_id = ?1", nativeQuery = true)
    List<GroupNotification> findAllGroupNotiOfUser(Long fromUserId);

    @Query(value="select * from group_notifications where group_id = ?1", nativeQuery = true)
    List<GroupNotification> findAllGroupNotiOfGroup(Long groupId);
}
