package com.example.demo.controller;

import com.example.demo.model.dto.PostRequest;
import com.example.demo.model.dto.PostResponseDto;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@CrossOrigin("*")
public class PostRestController {
    @Autowired
    private IPostService postService;

    @Autowired
    private IUSerService userService;

    @Autowired
    private IImageService imageService;

    @Value("C:/Users/nguye/OneDrive/Desktop/Image-upload/")
    private String uploadPath;
    @GetMapping("/allPostOfFriend/user/{userId}")
    public ResponseEntity<List<PostResponseDto>> findAllPostOfFriend(@PathVariable Long userId) {
        List<Post> posts = this.postService.findAllPostOfFriends(userId);
        List<PostResponseDto> postResponseDtoList = this.postService.mapperListPostEntityToDto(posts);
        return new ResponseEntity<>(postResponseDtoList, HttpStatus.OK);
    }
    @GetMapping("/allPost/user/{userId}")
    public ResponseEntity<List<PostResponseDto>> findAllPostOfUser(@PathVariable Long userId) {
        List<Post> posts = this.postService.findAllPostByUser(userId);
        List<PostResponseDto> postResponseDtoList = this.postService.mapperListPostEntityToDto(posts);
        return new ResponseEntity<>(postResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponseDto> findPostByPostId(@PathVariable Long postId) {
        PostResponseDto postResponseDto = this.postService.findPostById(postId);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }
    @GetMapping("/allPostAboutFriend/user/{userId}")
    public ResponseEntity<List<PostResponseDto>> findAllPostAboutFriend(@PathVariable Long userId) {
        List<Post> posts = this.postService.findAllPostAboutFriend(userId);
        List<PostResponseDto> postResponseDtoList = this.postService.mapperListPostEntityToDto(posts);
        return new ResponseEntity<>(postResponseDtoList, HttpStatus.OK);
    }
    @GetMapping("/allPostAboutUser/user/{userId}")
    public ResponseEntity<List<PostResponseDto>> findAllPostAboutUser(@PathVariable Long userId) {
        List<Post> posts = this.postService.findAllPostAboutUser(userId);
        List<PostResponseDto> postResponseDtoList = this.postService.mapperListPostEntityToDto(posts);
        return new ResponseEntity<>(postResponseDtoList, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Post> createNewPost(@PathVariable Long userId, @ModelAttribute PostRequest postRequest) {
        Optional<User> userOptional = this.userService.findById(userId);
        Post newPost = new Post();
        newPost.setContent(postRequest.getContent());
        newPost.setDateCreated(new Date());
        newPost.setStatusPost(postRequest.getStatusPost());
        newPost.setUser(userOptional.get());
        this.postService.save(newPost);
        MultipartFile[] listImageFile = postRequest.getImages();
        List<String> listFileName = new ArrayList<>();
        if (listImageFile != null) {
            // đổi tên ảnh sang string và add vào list tên ảnh
            for (int i = 0; i < listImageFile.length; i++) {
                listFileName.add(System.currentTimeMillis() + listImageFile[i].getOriginalFilename());
            }
            // save ảnh vào trong database và lưu ảnh vào thư mục chứa ảnh
            for (int i = 0; i < listFileName.size(); i++) {
                this.imageService.save(new Images(listFileName.get(i), newPost));
                try {
                    FileCopyUtils.copy(listImageFile[i].getBytes(), new File(uploadPath + listFileName.get(i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PutMapping("/editPost/post/{postId}")
    public ResponseEntity<Post> editPost(@PathVariable Long postId, @ModelAttribute PostRequest postRequest) {
        Optional<Post> postOptional = this.postService.findById(postId);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        postOptional.get().setStatusPost(postRequest.getStatusPost());
        postOptional.get().setContent(postRequest.getContent());
        this.postService.save(postOptional.get());
        MultipartFile[] imageFiles = postRequest.getImages();
        List<String> listImageFileName = new ArrayList<>();
        if (imageFiles != null) {
            // đổi tên ảnh sang string và add vào list tên ảnh
            for (int i = 0; i < imageFiles.length; i++) {
                listImageFileName.add(System.currentTimeMillis() + imageFiles[i].getOriginalFilename());
            }
            // save ảnh vào trong database và lưu ảnh vào thư mục chứa ảnh
            for (int i = 0; i < listImageFileName.size(); i++) {
                this.imageService.save(new Images(listImageFileName.get(i), postOptional.get()));
                try {
                    FileCopyUtils.copy(imageFiles[i].getBytes(), new File(uploadPath + listImageFileName.get(i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResponseEntity<>(postOptional.get(), HttpStatus.OK);
    }

    @DeleteMapping("/deletePost/post/{postId}")
    public ResponseEntity<Post> deletePost(@PathVariable Long postId) {
        Optional<Post> postOptional = this.postService.findById(postId);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.postService.remove(postId);
        return new ResponseEntity<>(postOptional.get(), HttpStatus.OK);
    }

}
