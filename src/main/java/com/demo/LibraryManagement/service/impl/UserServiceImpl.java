package com.demo.LibraryManagement.service.impl;

import com.demo.LibraryManagement.DTO.UserDTO;
import com.demo.LibraryManagement.Entity.User;
import com.demo.LibraryManagement.Exception.ResourceNotFoundException;
import com.demo.LibraryManagement.Repository.BookRepository;
import com.demo.LibraryManagement.Repository.UserRepository;
import com.demo.LibraryManagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userrepository;

    private final ModelMapper modelmapper;

    public UserServiceImpl(UserRepository userrepository, ModelMapper modelmapper) {
        this.userrepository = userrepository;
        this.modelmapper = modelmapper;
    }

    @Override
    public UserDTO RegisterUser(UserDTO user) {
        User mapuser = modelmapper.map(user, User.class);
        User saveuser = userrepository.save(mapuser);
        UserDTO map = modelmapper.map(saveuser, UserDTO.class);
        return map;
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> alluser = userrepository.findAll();
        List<UserDTO> collected = alluser.stream().map(userentity -> modelmapper.map(userentity, UserDTO.class)).collect(Collectors.toList());
        return collected;
    }

    @Override
    public UserDTO getUserById(Long userid) {
        Optional<User> userExist = isUserExist(userid);
        if(userExist.isPresent()){
            User user = userExist.get();
            UserDTO map = modelmapper.map(user, UserDTO.class);
            return map;
        }else {
            throw new ResourceNotFoundException("User with given id not found");
        }

    }

    @Override
    public UserDTO updateUserProfile(UserDTO userdto,Long id) {
        boolean existsById = userrepository.existsById(id);
        if(existsById){
            User user = userrepository.findById(id).get();
            user.setId(id);
            User saveuser = userrepository.save(user);
            return modelmapper.map(saveuser,UserDTO.class);
        }
        else {
            throw new ResourceNotFoundException("User with given id not Found");
        }
    }
    @Override
    public String removeUser(Long id) {
        boolean existsById = userrepository.existsById(id);
        if(existsById){
            userrepository.deleteById(id);
            return "User deleted Successfully";
        }
        return null;
    }

    private Optional<User> isUserExist(Long userid){
       return userrepository.findById(userid);
    }
}
