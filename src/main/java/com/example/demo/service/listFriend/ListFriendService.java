package com.example.demo.service.listFriend;

import com.example.demo.model.entity.ListFriend;
import com.example.demo.repository.IListFriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListFriendService implements IListFriendService{
    @Autowired
    private IListFriendRepository listFriendRepository;

    @Override
    public ListFriend save(ListFriend newFriend) {
        return this.listFriendRepository.save(newFriend);
    }

    @Override
    public List<ListFriend> listFriendsOfUser(Long userId) {
        return this.listFriendRepository.listFriendsOfUser(userId);
    }

    @Override
    public void remove(Long id) {
        this.listFriendRepository.deleteById(id);
    }

    @Override
    public Optional<ListFriend> findById(Long id) {
        return this.listFriendRepository.findById(id);
    }

    @Override
    public Optional<ListFriend> findFriendByFromUserAndToUser(Long userId, Long friendId) {
        return this.listFriendRepository.findFriendByFromUserAndToUser(userId, friendId);
    }
}
