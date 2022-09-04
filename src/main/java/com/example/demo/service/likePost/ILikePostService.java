package com.example.demo.service.likePost;

import com.example.demo.model.entity.LikePost;

import java.util.List;
import java.util.Optional;

public interface ILikePostService {
    List<LikePost> findAllLikeOfPost(Long postId);

    Optional<LikePost> findLikeByUserIdAndPostId(Long userId, Long postId);

    void remove(Long id);

    LikePost save(LikePost likePost);

    Integer totalLikeOfPost(Long postId);
}
