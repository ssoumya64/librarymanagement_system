package com.demo.LibraryManagement.service.impl;

import com.demo.LibraryManagement.DTO.*;
import com.demo.LibraryManagement.Entity.Fine;
import com.demo.LibraryManagement.Entity.Librarian;
import com.demo.LibraryManagement.Exception.ResourceNotFoundException;
import com.demo.LibraryManagement.Repository.LibrarianRepository;
import com.demo.LibraryManagement.Repository.UserRepository;
import com.demo.LibraryManagement.enums.BookStatus;
import com.demo.LibraryManagement.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LibrarianServiceImpl implements LibrarianService {
    private final BookService bookService;
    private final BorrowedRecordService borrowService;
    private final FineService fineService;
    private final UserService userService;
    private final LibrarianRepository librarianRepository;
    private final ModelMapper modelmapper;

    public LibrarianServiceImpl(BookService bookService, BorrowedRecordService borrowService, FineService fineService, UserService userService, UserRepository userrepository, UserRepository userRepository, LibrarianRepository librarianRepository, ModelMapper modelmapper) {
        this.bookService = bookService;
        this.borrowService = borrowService;
        this.fineService = fineService;
        this.userService = userService;
        this.librarianRepository = librarianRepository;
        this.modelmapper = modelmapper;
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        return bookService.createbooks(bookDTO);

    }

    @Override
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) {
      return  bookService.updateBook(bookId,bookDTO);

    }

    @Override
    public String deleteBook(Long bookId) {
      return bookService.deleteBook(bookId);
    }

    @Override
    public List<BookDTO> searchBooks(String title, String author, String category, String isbn, BookStatus status) {
        return bookService.searchBooks(title, author, category, isbn, status);
    }

    @Override
    public BorrowRecordDTO issueBook(Long Userid,Long bookid) {
        return borrowService.borrowBook(Userid, bookid);
    }

    @Override
    public BorrowRecordDTO returnBook(Long borrowRecordId) {
        return borrowService.returnBook(borrowRecordId);
    }

    @Override
    public List<BorrowRecordDTO> getBorrowHistory(Long userId) {
        return borrowService.getUserBorrowHistory(userId);
    }

    @Override
    public List<FineDTO> getUnpaidFines(Long userId) {
        return fineService.getUnpaidFines(userId);
    }

    @Override
    public FineDTO payFine(Long fineId) {
        Fine fine = fineService.payFine(fineId);
        return modelmapper.map(fine,FineDTO.class);
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
      return  userService.RegisterUser(userDTO);

    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        return userService.updateUserProfile(userDTO,userId);
    }

    @Override
    public String deactivateUser(Long userId) {
        userService.deactivateUser(userId);
        return "User with id"+userId+ "has been deactivated";
    }

    @Override
    public LibrarianDTO getLibrarianDetails(Long id) {
        Librarian librarian = librarianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Librarian not found with id: " + id));
        return modelmapper.map(librarian,LibrarianDTO.class);
    }

    @Override
    public void changePassword(Long id, String newPassword) {
        Librarian librarian = librarianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Librarian not found with id: " + id));
        librarian.setPassword(newPassword); // assume password encoding handled elsewhere
        librarianRepository.save(librarian);

    }

    @Override
    public void activateLibrarianAccount(Long id, Boolean isActive) {
        Librarian librarian = librarianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Librarian not found with id: " + id));
        librarian.setIsActive(isActive);
        librarianRepository.save(librarian);
    }
    @Override
    public Librarian registerLibrarian(LibrarianDTO registrationDTO) {
        // Check if the email is already in use
        if (librarianRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        // Map DTO to Entity
        Librarian librarian = modelmapper.map(registrationDTO, Librarian.class);
        // Optionally, you may want to hash the password before saving it
        librarian.setPassword(hashPassword(registrationDTO.getPassword()));

        // Save the new librarian
        return librarianRepository.save(librarian);
    }



    private String hashPassword(String password) {
        // Use a secure hashing algorithm, e.g., BCrypt
        return password; // Replace with actual hashing logic
    }
}
