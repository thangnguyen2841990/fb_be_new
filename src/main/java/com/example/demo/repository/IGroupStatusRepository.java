package com.example.demo.repository;

import com.example.demo.model.entity.GroupStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupStatusRepository extends JpaRepository<GroupStatus, Long> {
}
