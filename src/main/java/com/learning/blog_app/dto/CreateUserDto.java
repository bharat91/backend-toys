package com.learning.blog_app.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

    private String name;
    private String username;
    private String email;

}
