package com.example.demo.repository;

import com.example.demo.model.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILikeCommentRepository extends JpaRepository<LikeComment, Long> {

    @Query(value = "select * from like_comments where from_user_id = ?1 and comment_id = ?2", nativeQuery = true)
    Optional<LikeComment> findLikeCommentByCommentIdAndUserId(Long userID, Long commentId);
    @Query(value = "select * from like_comments where comment_id = ?1", nativeQuery = true)
    List<LikeComment> findAllLikeOfComment(Long commentId);
}
