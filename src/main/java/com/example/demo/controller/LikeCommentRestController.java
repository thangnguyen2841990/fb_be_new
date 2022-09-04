package com.example.demo.controller;

import com.example.demo.model.entity.*;
import com.example.demo.service.comment.ICommentService;
import com.example.demo.service.likeComment.ILikeCommentService;
import com.example.demo.service.notification.INotificationService;
import com.example.demo.service.post.IPostService;
import com.example.demo.service.user.IUSerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/likeComments")
public class LikeCommentRestController {
    @Autowired
    private ILikeCommentService likeCommentService;
    @Autowired
    private IUSerService userService;
    @Autowired
    private IPostService postService;
    @Autowired
    private ICommentService commentService;

    @Autowired
    private INotificationService notificationService;

    @PostMapping("/createLikeComment/user/{userId}/post/{postId}/comment/{commentId}")
    public ResponseEntity<LikeComment> createLikeComment(@PathVariable Long userId, @PathVariable Long postId, @PathVariable Long commentId) {
        Optional<LikeComment> likeCommentOptional = this.likeCommentService.findLikeCommentByCommentIdAndUserId(userId, commentId);
        LikeComment likeComment = new LikeComment();
        if (!likeCommentOptional.isPresent()) {
            Optional<User> userOptional = this.userService.findById(userId);
            Optional<Comment> commentOptional = this.commentService.findById(commentId);
            Optional<Post> postOptional = this.postService.findById(postId);
            likeComment.setFromUser(userOptional.get());
            likeComment.setPost(postOptional.get());
            likeComment.setComment(commentOptional.get());
            this.likeCommentService.save(likeComment);
            if (userOptional.get().getId() != commentOptional.get().getFormUser().getId()) {
                Notification newNotification = new Notification();
                newNotification.setDateCreated(new Date());
                newNotification.setContent(likeComment.getFromUser().getFullName()+ " đã thích bình luận của bạn!");
                newNotification.setToUser(commentOptional.get().getFormUser());
                newNotification.setFromUser(userOptional.get());
                newNotification.setStatus(1);
                this.notificationService.save(newNotification);
            }
        } else {
            this.likeCommentService.remove(likeCommentOptional.get().getId());
        }
        return new ResponseEntity<>(likeComment, HttpStatus.CREATED);
    }

}
