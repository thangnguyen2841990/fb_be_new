package com.example.demo.model.dto;

import com.example.demo.model.entity.StatusPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackGroundRequest {
    private MultipartFile backGround;

    private StatusPost statusPost;

}
