package com.example.demo.repository;

import com.example.demo.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);

    Optional<User> findByUsername(String username);

    @Query(value = "select * from users where id <> ?1 and id<> 6 and id<>7 and username <> 'admin'and id not in (select friend_of_user_id from list_friends where user_id = ?1) ", nativeQuery = true)
    List<User> findAllUserOtherCurrenUserAndFriends(Long currenUserId);
    @Query(value="call search_by_name(?1)",nativeQuery = true)
    List<User> searchByFullName(String name);
}
