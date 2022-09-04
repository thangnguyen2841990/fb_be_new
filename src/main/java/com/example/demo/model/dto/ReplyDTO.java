package com.example.demo.model.dto;

import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    private Long id;

    private String content;

    private String dateCreated;


    private User fromUser;


    private Comment comment;


    private Post post;
}
