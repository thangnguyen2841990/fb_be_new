package com.example.demo.model.dto;

import com.example.demo.model.entity.StatusPost;
import com.example.demo.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;

    private String content;

    private String dateCreated;

    private StatusPost statusPost;

    private User user;
}
