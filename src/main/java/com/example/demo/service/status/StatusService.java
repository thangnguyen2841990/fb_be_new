package com.example.demo.service.status;

import com.example.demo.model.entity.StatusPost;
import com.example.demo.repository.IStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService implements IStatusService{
    @Autowired
    private IStatusRepository statusRepository;
    @Override
    public List<StatusPost> findAll() {
        return statusRepository.findAll();
    }

    @Override
    public Optional<StatusPost> findById(Long id) {
        return this.statusRepository.findById(id);
    }

    @Override
    public StatusPost save(StatusPost statusPost) {
        return this.statusRepository.save(statusPost);
    }

    @Override
    public void remove(Long id) {
        this.statusRepository.deleteById(id);
    }
}
