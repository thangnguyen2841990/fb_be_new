package com.example.demo.service.status;

import com.example.demo.model.entity.StatusPost;

import java.util.List;
import java.util.Optional;

public interface IStatusService {

    List<StatusPost> findAll();

    Optional<StatusPost> findById(Long id);

    StatusPost save(StatusPost statusPost);

    void remove(Long id);

}
