package com.example.demo.service.likeComment;

import com.example.demo.model.dto.TotalLikeComment;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.LikeComment;
import com.example.demo.model.entity.Post;

import java.util.List;
import java.util.Optional;

public interface ILikeCommentService {

    LikeComment save(LikeComment newLikeComment);

    void remove(Long id);

    Optional<LikeComment> findLikeCommentByCommentIdAndUserId(Long userID, Long commentId);

    TotalLikeComment findTotalLikeOfComment(Long commentId);

    List<TotalLikeComment>  listTotalLikeComments(List<Comment> comments);

}
