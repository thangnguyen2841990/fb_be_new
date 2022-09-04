package com.example.demo.repository;

import com.example.demo.model.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReplyRepository extends JpaRepository<Reply, Long> {
    @Query(value = "select * from replies where post_id = ?1 order by date_created desc", nativeQuery = true)
    List<Reply> findAllReplyOfPost(Long postId);

    @Query(value = "select * from replies where comment_id = ?1", nativeQuery = true)
    List<Reply> findALlReplyOfComment(Long commentId);
}
