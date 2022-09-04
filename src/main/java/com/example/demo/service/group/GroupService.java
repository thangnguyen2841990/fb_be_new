package com.example.demo.service.group;

import com.example.demo.model.entity.Group;
import com.example.demo.repository.IGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements IGroupService{
    @Autowired
    private IGroupRepository groupRepository;
    @Override
    public Group save(Group newGroup) {
        return this.groupRepository.save(newGroup);
    }

    @Override
    public void remove(Long groupId) {
        this.groupRepository.deleteById(groupId);
    }

    @Override
    public Optional<Group> findById(Long groupId) {
        return this.groupRepository.findById(groupId);
    }

    @Override
    public List<Group> findAllGroupOfUser(Long userId) {
        return this.groupRepository.findAllGroupOfUser(userId);
    }

    @Override
    public List<Group> findAllGroupOtherUser(Long userId) {
        return this.groupRepository.findAllGroupOtherUser(userId);
    }
}
