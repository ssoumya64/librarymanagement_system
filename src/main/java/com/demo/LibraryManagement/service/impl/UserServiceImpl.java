package com.demo.LibraryManagement.service.impl;

import com.demo.LibraryManagement.DTO.FineDTO;
import com.demo.LibraryManagement.DTO.UserDTO;
import com.demo.LibraryManagement.Entity.BorrowRecord;
import com.demo.LibraryManagement.Entity.Fine;
import com.demo.LibraryManagement.Entity.User;
import com.demo.LibraryManagement.Exception.ResourceNotFoundException;
import com.demo.LibraryManagement.Repository.BookRepository;
import com.demo.LibraryManagement.Repository.BorrowRecordrepository;
import com.demo.LibraryManagement.Repository.FineRepository;
import com.demo.LibraryManagement.Repository.UserRepository;
import com.demo.LibraryManagement.enums.FineStatus;
import com.demo.LibraryManagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userrepository;

    private final BorrowRecordrepository borrowRecordRepository;
    private final FineRepository fineRepository;

    private final ModelMapper modelmapper;

    public UserServiceImpl(UserRepository userrepository, BorrowRecordrepository borrowRecordrepository, BorrowRecordrepository borrowRecordRepository, FineRepository fineRepository, ModelMapper modelmapper) {
        this.userrepository = userrepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.fineRepository = fineRepository;
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

    @Override
    public void deactivateUser(Long userId) {
        User user = userrepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Check if user has any active borrow records
        List<BorrowRecord> activeBorrowRecords = borrowRecordRepository.findByUserId(userId);
        if (!activeBorrowRecords.isEmpty()) {
            throw new IllegalStateException("User cannot be deactivated while having active borrow records.");
        }

        // Optionally, you may want to check for unpaid fines
        List<FineDTO> unpaidFines = fineRepository.findByUserIdAndFinsestatus(userId, FineStatus.UNPAID);
        if (!unpaidFines.isEmpty()) {
            throw new IllegalStateException("User cannot be deactivated while having unpaid fines.");
        }

        // Deactivate user
        user.setIsActive(false); // Assuming you have an 'active' field in the User entity
        userrepository.save(user);
    }

    private Optional<User> isUserExist(Long userid){
       return userrepository.findById(userid);
    }
}
