package com.example.demo.service.comment;

import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.dto.ReplyDTO;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.LikeComment;
import com.example.demo.model.entity.Reply;
import com.example.demo.repository.ICommentRepository;
import com.example.demo.repository.ILikeCommentRepository;
import com.example.demo.repository.IReplyRepository;
import com.example.demo.service.likeComment.ILikeCommentService;
import com.example.demo.service.post.IPostService;
import com.example.demo.service.reply.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private IPostService postService;

    @Autowired
    private IReplyService replyService;

    @Autowired
    private IReplyRepository replyRepository;

    @Autowired
    private ILikeCommentRepository likeCommentRepository;

    @Override
    public List<Comment> findAllCommentOfPost(Long postId) {
        return commentRepository.AllCommentOfPost(postId);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment save(Comment newComment) {
        return commentRepository.save(newComment);
    }

    @Override
    public void remove(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDTO mapperCommentEntityToDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setPost(comment.getPost());
        commentDTO.setDateCreated(this.postService.getDiffDays(comment.getDateCreated(), new Date()));
        commentDTO.setFormUser(comment.getFormUser());
        commentDTO.setContent(comment.getContent());
        List<Reply> replyList = this.replyService.findALlReplyOfComment(comment.getId());
        commentDTO.setTotalReply(replyList.size());
        List<LikeComment> likeCommentList = this.likeCommentRepository.findAllLikeOfComment(comment.getId());
        commentDTO.setTotalLike(likeCommentList.size());
        List<ReplyDTO> replyDTOList = this.replyService.mapperReplyEntityToDto(replyList);
        commentDTO.setListReply(replyDTOList);
        return  commentDTO;
    }

    @Override
    public List<CommentDTO> mapperCommentEntityToDto(List<Comment> comment) {
        List<CommentDTO> comments = new ArrayList<>();
        for (int i = 0; i < comment.size(); i++) {
            comments.add(mapperCommentEntityToDto(comment.get(i)));
        }
        return comments;
    }

    @Override
    public Integer totalComment(Long postId) {
        List<Comment> comments = findAllCommentOfPost(postId);
        return comments.size();
    }
}
