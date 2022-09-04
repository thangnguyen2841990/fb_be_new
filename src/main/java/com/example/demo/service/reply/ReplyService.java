package com.example.demo.service.reply;


import com.example.demo.model.dto.ReplyDTO;
import com.example.demo.model.dto.TotalReply;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.Reply;
import com.example.demo.repository.IReplyRepository;
import com.example.demo.service.comment.ICommentService;
import com.example.demo.service.post.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyService implements IReplyService {
    @Autowired
    private IReplyRepository replyRepository;
    @Autowired
    private IPostService postService;

    @Autowired
    private ICommentService commentService;

    @Override
    public List<Reply> findAllReplyOfPost(Long postId) {
        return replyRepository.findAllReplyOfPost(postId);
    }

    @Override
    public Reply save(Reply newReply) {
        return replyRepository.save(newReply);
    }

    @Override
    public Optional<Reply> findById(Long replyId) {
        return this.replyRepository.findById(replyId);
    }

    @Override
    public void remove(Long id) {
        replyRepository.deleteById(id);
    }

    @Override
    public ReplyDTO mapperReplyEntityToDto(Reply reply) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setId(reply.getId());
        replyDTO.setComment(reply.getComment());
        replyDTO.setPost(reply.getPost());
        replyDTO.setDateCreated(postService.getDiffDays(reply.getDateCreated(), new Date()));
        replyDTO.setFromUser(reply.getFromUser());
        replyDTO.setContent(reply.getContent());
        return replyDTO;
    }

    @Override
    public List<ReplyDTO> mapperReplyEntityToDto(List<Reply> replies) {
        List<ReplyDTO> replyDTOList = new ArrayList<>();
        for (int i = 0; i < replies.size(); i++) {
            replyDTOList.add(mapperReplyEntityToDto(replies.get(i)));
        }
        return replyDTOList;
    }

    @Override
    public List<Reply> findALlReplyOfComment(Long commentId) {
        return this.replyRepository.findALlReplyOfComment(commentId);
    }

    @Override
    public TotalReply totalReply(Long commentId) {
        TotalReply totalReply = new TotalReply();
        Optional<Comment> commentOptional = this.commentService.findById(commentId);
        totalReply.setComment(commentOptional.get());
        totalReply.setTotalReply(findALlReplyOfComment(commentId).size());
        return totalReply;
    }

    @Override
    public List<TotalReply> listTotalReply(List<Comment> comments) {
        List<TotalReply> listTotalReply = new ArrayList<>();
        for (int i = 0; i < comments.size(); i++) {
            listTotalReply.add(totalReply(comments.get(i).getId()));
        }
        return listTotalReply;
    }
}
