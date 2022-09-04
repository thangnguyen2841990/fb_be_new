package com.example.demo.model.dto;

import com.example.demo.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalReply {
    private Comment comment;

    private Integer totalReply;
}
