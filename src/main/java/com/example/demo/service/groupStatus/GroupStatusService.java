package com.example.demo.service.groupStatus;

import com.example.demo.model.entity.GroupStatus;
import com.example.demo.repository.IGroupStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupStatusService implements IGroupStatusService {
    @Autowired
    private IGroupStatusRepository groupStatusRepository;

    @Override
    public GroupStatus save(GroupStatus newGroupStatus) {
        return this.groupStatusRepository.save(newGroupStatus);
    }

    @Override
    public List<GroupStatus> findAll() {
        return this.groupStatusRepository.findAll();
    }

}
