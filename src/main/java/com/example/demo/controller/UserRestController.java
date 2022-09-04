package com.example.demo.controller;

import com.example.demo.model.dto.AvatarRequest;
import com.example.demo.model.dto.BackGroundRequest;
import com.example.demo.model.entity.Images;
import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.User;
import com.example.demo.service.image.IImageService;
import com.example.demo.service.post.IPostService;
import com.example.demo.service.user.IUSerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    private IUSerService userService;

    @Autowired
    private IPostService postService;

    @Autowired
    private IImageService imageService;

    @Value("C:/Users/nguye/OneDrive/Desktop/Image-upload/")
    String uploadPath;

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> findById(@PathVariable Long userId) {
        Optional<User> userOptional = this.userService.findById(userId);
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/getAllUser/currentUser/{currentUserId}")
    public ResponseEntity<List<User>> findAllUserOtherCurrentUserAndFriend(@PathVariable Long currentUserId) {
        List<User> users = this.userService.findAllUserOtherCurrenUserAndFriends(currentUserId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/searchByName/currentUser/{currentUserId}")
    public ResponseEntity<List<User>> searchByName(@RequestParam("name") String name, @PathVariable Long currentUserId) {
        Optional<User> currentUser = this.userService.findById(currentUserId);
        List<User> users = this.userService.searchByFullName(name);
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getUsername().equals(currentUser.get().getUsername())) {
                users.remove(i);
            }
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/changeAvatar/user/{userId}")
    public ResponseEntity<User> changeAvatar(@ModelAttribute AvatarRequest avatarRequest, @PathVariable Long userId) {
        Optional<User> userOptional = this.userService.findById(userId);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MultipartFile imageFile = avatarRequest.getAvatar();
        String avatarFileName = imageFile.getOriginalFilename();
        String avatar = System.currentTimeMillis() + avatarFileName;
        try {
            FileCopyUtils.copy(imageFile.getBytes(), new File(uploadPath + avatar));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userOptional.get().setAvatar(avatar);

        Post newPost = new Post();
        newPost.setContent(userOptional.get().getFullName() + " đã thay đổi ảnh đại diện!");
        newPost.setDateCreated(new Date());
        newPost.setStatusPost(avatarRequest.getStatusPost());
        newPost.setUser(userOptional.get());
        this.postService.save(newPost);
        Images newImage = new Images();
        newImage.setPost(newPost);
        newImage.setImage(avatar);
        this.imageService.save(newImage);
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

    @PostMapping("/changeBackground/user/{userId}")
    public ResponseEntity<User> changeBackground(@PathVariable Long userId, @ModelAttribute BackGroundRequest backGroundRequest) {
        Optional<User> userOptional = this.userService.findById(userId);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MultipartFile imageFile = backGroundRequest.getBackGround();
        String backgroundFileName = imageFile.getOriginalFilename();
        String background = System.currentTimeMillis() + backgroundFileName;
        try {
            FileCopyUtils.copy(imageFile.getBytes(), new File(uploadPath + background));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userOptional.get().setBackGround(background);
        Post newPost = new Post();
        newPost.setContent(userOptional.get().getFullName() + " đã thay đổi ảnh bìa!");
        newPost.setUser(userOptional.get());
        newPost.setDateCreated(new Date());
        newPost.setStatusPost(backGroundRequest.getStatusPost());
        this.postService.save(newPost);
        Images images = new Images();
        images.setPost(newPost);
        images.setImage(background);
        this.imageService.save(images);
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }
}
