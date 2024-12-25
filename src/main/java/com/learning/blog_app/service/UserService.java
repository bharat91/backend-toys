package com.learning.blog_app.service;

import com.learning.blog_app.dto.CreateUserDto;
import com.learning.blog_app.dto.UserDetailsDto;
import com.learning.blog_app.entity.UserEntity;
import com.learning.blog_app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository usersRepo;


    private ModelMapper modelMapper;

    public UserService(UserRepository usersRepo, ModelMapper modelMapper) {
        this.usersRepo = usersRepo;
        this.modelMapper = modelMapper;
    }

    public UserDetailsDto findUserByUsername(String username){
        UserEntity userEntity = usersRepo.findByUsername(username);
        UserDetailsDto response = modelMapper.map(userEntity,UserDetailsDto.class);
        return response;
    }

    public UserDetailsDto createUser(CreateUserDto createUser){

        UserEntity userEntity = modelMapper.map(createUser, UserEntity.class);
        usersRepo.save(userEntity);
        UserDetailsDto response = modelMapper.map(userEntity,UserDetailsDto.class);
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

}
