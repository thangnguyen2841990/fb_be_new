package com.example.demo.model.dto;

import com.example.demo.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatDTO {
    private Long id;

    private User sender;

    private User receiver;

    private String content;

    private boolean status;

    private String time;
}
