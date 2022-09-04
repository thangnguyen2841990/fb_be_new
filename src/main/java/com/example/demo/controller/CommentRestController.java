package com.example.demo.controller;

import com.example.demo.model.dto.CommentRequest;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.Notification;
import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.User;
import com.example.demo.service.comment.ICommentService;
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
@RequestMapping("/comments")
public class CommentRestController {
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUSerService userService;

    @Autowired
    private IPostService postService;

    @Autowired
    private INotificationService notificationService;

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<Comment> findById(@PathVariable Long commentId) {
        Optional<Comment> commentOptional = this.commentService.findById(commentId);
        if (!commentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(commentOptional.get(), HttpStatus.OK);
    }
    @PostMapping("/createNewComment/user/{userId}/post/{postId}")
    public ResponseEntity<Comment> createNewComment(@PathVariable Long userId, @PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        Optional<User> userOptional = this.userService.findById(userId);
        Optional<Post> postOptional = this.postService.findById(postId);
        Comment newComment = new Comment();
        newComment.setDateCreated(new Date());
        newComment.setPost(postOptional.get());
        newComment.setFormUser(userOptional.get());
        newComment.setContent(commentRequest.getContent());
        this.commentService.save(newComment);
        if (userOptional.get().getId() != postOptional.get().getUser().getId()) {
            Notification newNotification = new Notification();
            newNotification.setDateCreated(new Date());
            newNotification.setContent(newComment.getFormUser().getFullName()+ " đã bình luận status của bạn!");
            newNotification.setToUser(postOptional.get().getUser());
            newNotification.setFromUser(userOptional.get());
            newNotification.setStatus(2);
            this.notificationService.save(newNotification);
        }
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @PutMapping("/editComment/comment/{commentId}")
    public ResponseEntity<Comment> editComment(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        Optional<Comment> commentOptional = this.commentService.findById(commentId);
        if (!commentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        commentOptional.get().setContent(commentRequest.getContent());
        this.commentService.save(commentOptional.get());
        return new ResponseEntity<>(commentOptional.get(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteComment/comment/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long commentId) {
            Optional<Comment> commentOptional = this.commentService.findById(commentId);
        if (!commentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.commentService.remove(commentId);
        return new ResponseEntity<>(commentOptional.get(), HttpStatus.OK);
    }
}
