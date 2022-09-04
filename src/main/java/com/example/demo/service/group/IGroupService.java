package com.example.demo.service.group;

import com.example.demo.model.entity.Group;

import java.util.List;
import java.util.Optional;

public interface IGroupService {
    Group save(Group newGroup);

    void remove(Long groupId);

    Optional<Group> findById(Long groupId);

    List<Group> findAllGroupOfUser(Long userId);

    List<Group> findAllGroupOtherUser(Long userId);


}
