package com.example.demo.model.dto;


import com.example.demo.model.entity.GroupStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequest {
    private String name;

    private GroupStatus groupStatus;
}
