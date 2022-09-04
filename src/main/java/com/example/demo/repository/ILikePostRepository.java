package com.example.demo.repository;

import com.example.demo.model.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILikePostRepository extends JpaRepository<LikePost, Long> {
    @Query(value = "select * from like_posts where FROM_USER_ID = ?1 and POST_ID =?2", nativeQuery = true)
    Optional<LikePost> findLikePosts(Long fromUserId, Long postId);
    @Query(value = "select * from like_posts where post_id = ?1", nativeQuery = true)
    List<LikePost> findAllLikeOfPost(Long postId);
}
