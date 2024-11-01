package com.demo.LibraryManagement.service;

import com.demo.LibraryManagement.DTO.*;
import com.demo.LibraryManagement.Entity.Librarian;
import com.demo.LibraryManagement.enums.BookStatus;

import java.util.List;

public interface LibrarianService {
    // Book management methods
    public BookDTO addBook(BookDTO bookDTO) ;
    public BookDTO updateBook(Long bookId, BookDTO bookDTO);
    public String deleteBook(Long bookId);
    List<BookDTO> searchBooks(String title, String author, String category, String isbn, BookStatus status);

    // Borrow management methods
    public BorrowRecordDTO issueBook(Long userid,Long bookid);
    public BorrowRecordDTO returnBook(Long borrowRecordId) ;
    public List<BorrowRecordDTO> getBorrowHistory(Long userId) ;

    // Fine management methods
    public List<FineDTO> getUnpaidFines(Long userId);
    public FineDTO payFine(Long fineId) ;

    // User management methods
    public UserDTO addUser(UserDTO userDTO);
    public UserDTO updateUser(Long userId, UserDTO userDTO);
    public String deactivateUser(Long userId);

    // Librarian account management
    public LibrarianDTO getLibrarianDetails(Long id);
    public void changePassword(Long id, String newPassword);
    public void activateLibrarianAccount(Long id, Boolean isActive);
    Librarian registerLibrarian(LibrarianDTO registrationDTO);


}
