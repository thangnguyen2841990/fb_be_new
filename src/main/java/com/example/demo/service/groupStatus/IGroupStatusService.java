package com.example.demo.service.groupStatus;


import com.example.demo.model.entity.GroupStatus;

import java.util.List;

public interface IGroupStatusService {
    GroupStatus save(GroupStatus newGroupStatus);

    List<GroupStatus> findAll();
}
