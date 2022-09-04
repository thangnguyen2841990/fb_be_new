package com.example.demo.controller;

import com.example.demo.model.dto.ReplyRequest;
import com.example.demo.model.entity.*;
import com.example.demo.service.comment.ICommentService;
import com.example.demo.service.notification.INotificationService;
import com.example.demo.service.post.IPostService;
import com.example.demo.service.reply.IReplyService;
import com.example.demo.service.user.IUSerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/replies")
public class ReplyRestController {
    @Autowired
    private IReplyService replyService;
    @Autowired
    private ICommentService commentService;

    @Autowired
    private IUSerService userService;
    @Autowired
    private IPostService postService;

    @Autowired
    private INotificationService notificationService;

    @GetMapping("/reply/{replyId}")
    public ResponseEntity<Reply> findReplyByReplyId(@PathVariable Long replyId) {
        Optional<Reply> replyOptional = this.replyService.findById(replyId);
        if (!replyOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(replyOptional.get(), HttpStatus.OK);
    }

    @PostMapping("/createReply/user/{userId}/comment/{commentId}/post/{postId}")
    public ResponseEntity<Reply> createNewReply(@PathVariable Long userId, @PathVariable Long commentId, @PathVariable Long postId, @RequestBody ReplyRequest replyRequest) {
        Reply newReply = new Reply();
        Optional<Post> postOptional = this.postService.findById(postId);
        Optional<User> userOptional = this.userService.findById(userId);
        Optional<Comment> commentOptional = this.commentService.findById(commentId);
        newReply.setDateCreated(new Date());
        newReply.setContent(replyRequest.getContent());
        newReply.setComment(commentOptional.get());
        newReply.setFromUser(userOptional.get());
        newReply.setPost(postOptional.get());
        this.replyService.save(newReply);
        if (userOptional.get().getId() != commentOptional.get().getFormUser().getId()) {
            Notification newNotification = new Notification();
            newNotification.setDateCreated(new Date());
            newNotification.setContent(newReply.getFromUser().getFullName()+ " đã phản hồi bình luận của bạn!");
            newNotification.setToUser(commentOptional.get().getFormUser());
            newNotification.setFromUser(userOptional.get());
            newNotification.setStatus(3);
            this.notificationService.save(newNotification);
        }
        return new ResponseEntity<>(newReply, HttpStatus.CREATED);
    }
    @PutMapping("/editReply/reply/{replyId}")
    public ResponseEntity<Reply> editReply(@PathVariable Long replyId, @RequestBody ReplyRequest replyRequest) {
        Optional<Reply> replyOptional = this.replyService.findById(replyId);
        if (!replyOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        replyOptional.get().setContent(replyRequest.getContent());
        this.replyService.save(replyOptional.get());
        return new ResponseEntity<>(replyOptional.get(), HttpStatus.OK);
    }
    @DeleteMapping("/deleteReply/reply/{replyId}")
    public ResponseEntity<Reply> deleteReply(@PathVariable Long replyId) {
        Optional<Reply> replyOptional = this.replyService.findById(replyId);
        if (!replyOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.replyService.remove(replyId);
        return new ResponseEntity<>(replyOptional.get(), HttpStatus.OK);
    }
}
