package com.example.demo.controller;

import com.example.demo.model.entity.LikePost;
import com.example.demo.model.entity.Notification;
import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.User;
import com.example.demo.service.likePost.ILikePostService;
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
@RequestMapping("/likePosts")
public class LikePostRestController {
    @Autowired
    private ILikePostService likePostService;
    @Autowired
    private IUSerService userService;
    @Autowired
    private IPostService postService;
    @Autowired
    private INotificationService notificationService;

    @PostMapping("/createNewLike/user/{userId}/post/{postId}")
    public ResponseEntity<LikePost> createNewLike(@PathVariable Long userId, @PathVariable Long postId) {
        LikePost newLikePost = new LikePost();
        Optional<LikePost> likePostOptional = this.likePostService.findLikeByUserIdAndPostId(userId, postId);
        if (!likePostOptional.isPresent()) {
            Optional<User> optionalUser = this.userService.findById(userId);
            Optional<Post> optionalPost = this.postService.findById(postId);
            newLikePost.setFromUser(optionalUser.get());
            newLikePost.setPost(optionalPost.get());
            this.likePostService.save(newLikePost);
            if (optionalUser.get().getId() != optionalPost.get().getUser().getId()) {
                Notification newNotification = new Notification();
                newNotification.setDateCreated(new Date());
                newNotification.setContent(newLikePost.getFromUser().getFullName()+ " đã thích status của bạn!");
                newNotification.setToUser(optionalPost.get().getUser());
                newNotification.setFromUser(optionalUser.get());
                newNotification.setStatus(0);
                this.notificationService.save(newNotification);
            }
        } else {
            this.likePostService.remove(likePostOptional.get().getId());
        }
        return new ResponseEntity<>(newLikePost, HttpStatus.CREATED);
    }
}
