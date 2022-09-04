package com.example.demo.service.comment;


import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface ICommentService {
    List<Comment> findAllCommentOfPost(Long postId);
    Optional<Comment> findById(Long id);
    Comment save(Comment newComment);

    void remove(Long id);
    CommentDTO mapperCommentEntityToDto(Comment comment);
    List<CommentDTO> mapperCommentEntityToDto(List<Comment> comments);

    Integer totalComment(Long postId);
}
