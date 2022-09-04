package com.example.demo.model.dto;

import com.example.demo.model.entity.Images;
import com.example.demo.model.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private PostDTO postDTO;

    private Integer totalLikePost;

    private List<CommentDTO> comments;

    private Integer totalComments;

    private List<Images> images;



    public PostResponseDto(PostDTO postDTO, List<Images> images) {
        this.postDTO = postDTO;
        this.images = images;
    }
}
