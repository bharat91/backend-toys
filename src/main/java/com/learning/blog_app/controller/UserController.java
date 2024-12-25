package com.learning.blog_app.controller;

import com.learning.blog_app.dto.CreateUserDto;
import com.learning.blog_app.dto.LoginUserDto;
import com.learning.blog_app.dto.UserDetailsDto;
import com.learning.blog_app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<UserDetailsDto> listAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/{username}")
    public UserDetailsDto getUserbyUsername(@PathVariable String username){
        return userService.findUserByUsername(username);
    }

    @PostMapping("")
    public ResponseEntity<UserDetailsDto> createUser(@RequestBody CreateUserDto createUser){

        UserDetailsDto res = userService.createUser(createUser);
        return ResponseEntity.created(URI.create("/users/" + res.getId())).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDetailsDto> verifyUser(
            @RequestBody LoginUserDto request
    ) {
        UserDetailsDto verifiedUser = userService.verifyUser(request);
        return ResponseEntity.ok(verifiedUser);
    }
}
