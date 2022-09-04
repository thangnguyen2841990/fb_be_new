package com.example.demo.service.likeComment;

import com.example.demo.model.dto.TotalLikeComment;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.LikeComment;
import com.example.demo.repository.ILikeCommentRepository;
import com.example.demo.service.comment.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LikeCommentService implements ILikeCommentService {
    @Autowired
    ILikeCommentRepository likeCommentRepository;

    @Autowired
    ICommentService commentService;

    @Override
    public LikeComment save(LikeComment newLikeComment) {
        return this.likeCommentRepository.save(newLikeComment);
    }

    @Override
    public void remove(Long id) {
        this.likeCommentRepository.deleteById(id);
    }

    @Override
    public Optional<LikeComment> findLikeCommentByCommentIdAndUserId(Long userID, Long commentId) {
        return this.likeCommentRepository.findLikeCommentByCommentIdAndUserId(userID, commentId);
    }

    @Override
    public TotalLikeComment findTotalLikeOfComment(Long commentId) {
        Optional<Comment> commentOptional = this.commentService.findById(commentId);
        TotalLikeComment totalLikeComment = new TotalLikeComment();
        List<LikeComment> likeCommentList = this.likeCommentRepository.findAllLikeOfComment(commentId);
        totalLikeComment.setComment(commentOptional.get());
        totalLikeComment.setTotalLikeComment(likeCommentList.size());
        return totalLikeComment;
    }

    @Override
    public List<TotalLikeComment> listTotalLikeComments(List<Comment> comments) {
        List<TotalLikeComment> listTotalLikeComments = new ArrayList<>();
        for (int i = 0; i < comments.size(); i++) {
            listTotalLikeComments.add(findTotalLikeOfComment(comments.get(i).getId()));
        }
        return listTotalLikeComments;
    }
}
