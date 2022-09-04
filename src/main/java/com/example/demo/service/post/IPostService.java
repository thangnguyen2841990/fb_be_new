package com.example.demo.service.post;

import com.example.demo.model.dto.PostDTO;
import com.example.demo.model.dto.PostResponseDto;
import com.example.demo.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPostService {
    List<Post> findAll();

    Page<Post> findAll(Pageable pageable);

    Optional<Post> findById(Long id);

    Post save(Post post);

    void remove(Long id);

    String getDiffDays(Date time1, Date time2);

    PostDTO mapperEntityToDTO(Post post);

    List<PostResponseDto> mapperListPostEntityToDto(List<Post> posts);

    List<Post> findAllPostByUser(Long userId);

    PostResponseDto findPostById(Long postId);

    List<Post> findAllPostOfFriends(Long userId);

    List<Post> findAllPostAboutFriend(Long userId);

    List<Post> findAllPostAboutUser(Long userId);




}
