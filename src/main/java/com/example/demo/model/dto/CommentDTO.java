package com.example.demo.model.dto;

import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id;

    private String content;

    private String dateCreated;

    private User formUser;

    private Post post;

    private Integer totalReply;

    private Integer totalLike;

    private List<ReplyDTO> listReply;
}
