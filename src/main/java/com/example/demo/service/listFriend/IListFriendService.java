package com.example.demo.service.listFriend;

import com.example.demo.model.entity.ListFriend;

import java.util.List;
import java.util.Optional;

public interface IListFriendService {
    ListFriend save(ListFriend newFriend);

    List<ListFriend> listFriendsOfUser(Long userId);

    void remove(Long id);

    Optional<ListFriend> findById(Long id);

    Optional<ListFriend> findFriendByFromUserAndToUser(Long userId, Long friendId);


}
