package com.example.demo.service.reply;

import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.dto.ReplyDTO;
import com.example.demo.model.dto.TotalReply;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.Reply;

import java.util.List;
import java.util.Optional;

public interface IReplyService {

    List<Reply> findAllReplyOfPost(Long postId);

    Reply save(Reply newReply);
    Optional<Reply> findById(Long replyId);
    void remove(Long id);
    ReplyDTO mapperReplyEntityToDto(Reply reply);
    List<ReplyDTO> mapperReplyEntityToDto(List<Reply> replies);

    List<Reply> findALlReplyOfComment(Long commentId);

    TotalReply totalReply(Long commentId);
    List<TotalReply> listTotalReply(List<Comment> comments);
}
