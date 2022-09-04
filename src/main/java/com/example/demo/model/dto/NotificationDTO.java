package com.example.demo.model.dto;

import com.example.demo.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long id;

    private String content;

    private Integer status;

    private String dateCreated;

    private User fromUser;

    private User toUser;
}
