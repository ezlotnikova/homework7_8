package com.gmail.zlotnikova.service;

import java.util.List;

import com.gmail.zlotnikova.service.model.UserDTO;

public interface UserService {

    UserDTO add(UserDTO addUserDTO);

    List<UserDTO> findAll();

    int deleteById(int id);

}