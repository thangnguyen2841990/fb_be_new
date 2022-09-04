package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {
    private String username;

    private String password;

    private String confirmPassword;

    private String email;

    private String phoneNumber;

    private String birthDay;

}
