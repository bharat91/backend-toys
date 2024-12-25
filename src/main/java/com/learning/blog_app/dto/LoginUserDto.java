package com.learning.blog_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginUserDto {
    private String username;
    private String password;
}
