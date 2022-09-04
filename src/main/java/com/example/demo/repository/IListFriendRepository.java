package com.example.demo.repository;

import com.example.demo.model.entity.ListFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IListFriendRepository extends JpaRepository<ListFriend, Long> {
    @Query(value = "select * from list_friends where user_id =?1",nativeQuery = true)
    List<ListFriend> listFriendsOfUser(Long userId);
    @Query(value = "select * from list_friends where user_id = ?1 and friend_of_user_id = ?2", nativeQuery = true)
    Optional<ListFriend> findFriendByFromUserAndToUser(Long userId, Long friendId);
}
