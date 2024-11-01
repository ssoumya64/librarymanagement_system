package com.demo.LibraryManagement.service;

import com.demo.LibraryManagement.DTO.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO RegisterUser(UserDTO user);

    List<UserDTO> getAllUser();
    UserDTO getUserById(Long userid);

    UserDTO updateUserProfile(UserDTO userdto,Long userid);

    String removeUser(Long id);
    void deactivateUser(Long userId);
}
