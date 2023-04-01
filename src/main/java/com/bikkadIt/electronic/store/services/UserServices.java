package com.bikkadIt.electronic.store.services;

import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.entities.User;
import com.bikkadIt.electronic.store.exception.ResourceNotFoundException;

import java.util.List;

public interface UserServices {
    //createUser
    UserDto createUser(UserDto userDto);

    //updateUser
    UserDto updateUser(UserDto userdto,long userId);

    //get single User by Id

    UserDto getByUserId(long userId);


    //get single User by email
    UserDto getUserByEmail(String email);
    //getAllUser

    PagebaleResponse<UserDto>getByAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    //deleteUser

    void deleteUser(long userId);

    List<UserDto>UserSerach(String keyword);




}
