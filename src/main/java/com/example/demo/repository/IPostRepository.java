package com.example.demo.repository;

import com.example.demo.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select * from posts where USER_ID = ?1 order by date_created desc ", nativeQuery = true)
    List<Post> findAllPostByUser(Long userId);
    @Query(value="select * from posts where (user_id in (6,7) or user_id in (select friend_of_user_id from list_friends where user_id = ?1)) and (status_post_id = 1 or status_post_id = 2) order by date_created desc", nativeQuery=true)
    List<Post> findAllPostOfFriends(Long userId);
    @Query(value = "select * from posts where user_id = ?1 and (status_post_id = 1 or status_post_id = 2)", nativeQuery = true)
    List<Post> findAllPostAboutFriend(Long userId);
    @Query(value = "select * from posts where user_id = ?1 and (status_post_id = 1)", nativeQuery = true)
    List<Post> findAllPostAboutUser(Long userId);
}
