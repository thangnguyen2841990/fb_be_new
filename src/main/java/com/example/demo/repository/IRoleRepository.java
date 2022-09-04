package com.example.demo.repository;

import com.example.demo.model.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.management.relation.RoleList;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Page<Role> findAll(Pageable pageable);
    @Query(value = "select * from roles where name = ?1", nativeQuery = true)
    Optional<Role> findByNameContaining(String name);
}
