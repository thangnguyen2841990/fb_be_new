package com.example.demo.service.likePost;

import com.example.demo.model.entity.LikePost;
import com.example.demo.repository.ILikePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikePostService implements ILikePostService{
    @Autowired
    ILikePostRepository likePostRepository;
    @Override
    public List<LikePost> findAllLikeOfPost(Long postId) {
        return this.likePostRepository.findAllLikeOfPost(postId);
    }

    @Override
    public Optional<LikePost> findLikeByUserIdAndPostId(Long userId, Long postId) {
        return this.likePostRepository.findLikePosts(userId, postId);
    }

    @Override
    public void remove(Long id) {
        this.likePostRepository.deleteById(id);
    }

    @Override
    public LikePost save(LikePost likePost) {
        return this.likePostRepository.save(likePost);
    }

    @Override
    public Integer totalLikeOfPost(Long postId) {
        List<LikePost> likePostList = findAllLikeOfPost(postId);
        return likePostList.size();
    }
}
