package com.learning.blog_app.service;

import com.learning.blog_app.dto.CreateUserDto;
import com.learning.blog_app.dto.LoginUserDto;
import com.learning.blog_app.dto.UserDetailsDto;
import com.learning.blog_app.entity.UserEntity;
import com.learning.blog_app.repository.UserRepository;
import com.learning.blog_app.security.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository usersRepo;


    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    public UserService(UserRepository usersRepo, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usersRepo = usersRepo;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserDetailsDto findUserByUsername(String username){
        UserEntity userEntity = usersRepo.findByUsername(username);
        UserDetailsDto response = modelMapper.map(userEntity,UserDetailsDto.class);
        return response;
    }

    public UserDetailsDto createUser(CreateUserDto createUser){

        UserEntity userEntity = modelMapper.map(createUser, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(createUser.getPassword()));
        UserEntity savedUser = usersRepo.save(userEntity);
        UserDetailsDto response = modelMapper.map(userEntity,UserDetailsDto.class);

        String token = jwtService.createJwt(savedUser.getUsername());
        response.setToken(token);
        return response;
    }

    public List<UserDetailsDto> getAllUsers(){
        List<UserEntity> allUsers = usersRepo.findAll();
        List<UserDetailsDto> dtos = allUsers
                .stream()
                .map(user -> modelMapper.map(user, UserDetailsDto.class))
                .collect(Collectors.toList());

        return dtos;
    }

    public UserDetailsDto verifyUser(LoginUserDto request){
        UserEntity userEntity = usersRepo.findByUsername(request.getUsername());

        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }
        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserDetailsDto userDetailsDto = modelMapper.map(userEntity,UserDetailsDto.class);
        userDetailsDto.setToken(userDetailsDto.getToken());

        return userDetailsDto;
    }


}
