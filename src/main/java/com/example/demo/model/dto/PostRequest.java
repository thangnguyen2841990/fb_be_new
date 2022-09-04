package com.example.demo.model.dto;

import com.example.demo.model.entity.StatusPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.ManyToOne;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String content;

    private StatusPost statusPost;

    private MultipartFile[] images;
}
