package com.ste.mzo.security.service;

import java.util.List;

import com.ste.mzo.security.dto.UserDto;
import com.ste.mzo.security.entity.User;



public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
