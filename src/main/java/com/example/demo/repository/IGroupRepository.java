package com.example.demo.repository;

import com.example.demo.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGroupRepository extends JpaRepository<Group, Long> {
    @Query(value = "select * from groups_ where admin_id = ?1", nativeQuery = true)
    List<Group> findAllGroupOfUser(Long userId);
    @Query(value = "select * from groups_ where admin_id <> ?1 and group_status_id = 1", nativeQuery = true)
    List<Group> findAllGroupOtherUser(Long userId);
}
