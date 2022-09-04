package com.example.demo.repository;

import com.example.demo.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
    @Query(value = "select * from notifications where to_user_id = ?1", nativeQuery = true)
    List<Notification> getAllNotificationOfUser(Long userId);
}
