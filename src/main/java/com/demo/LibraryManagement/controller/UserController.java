package com.demo.LibraryManagement.controller;

import com.demo.LibraryManagement.DTO.BookDTO;
import com.demo.LibraryManagement.DTO.BorrowRecordDTO;
import com.demo.LibraryManagement.DTO.UserDTO;
import com.demo.LibraryManagement.enums.BookStatus;
import com.demo.LibraryManagement.service.BookService;
import com.demo.LibraryManagement.service.BorrowedRecordService;
import com.demo.LibraryManagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
private final UserService userservice;

private final BookService bookService;
private final BorrowedRecordService borrowrecordservice;

    public UserController(UserService userservice, BookService bookService, BorrowedRecordService borrowrecordservice) {
        this.userservice = userservice;
        this.bookService = bookService;
        this.borrowrecordservice = borrowrecordservice;
    }
    @PostMapping("/createuser")
   public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userdto) {
       UserDTO registerUser = userservice.RegisterUser(userdto);
       return new ResponseEntity<>(registerUser,HttpStatus.CREATED);
   }
   @GetMapping("/getall")
   public ResponseEntity<List<UserDTO>> getalluser(){
       List<UserDTO> allUser = userservice.getAllUser();
       return new ResponseEntity<>(allUser,HttpStatus.FOUND);
   }
   @PutMapping("/update")
   public ResponseEntity<UserDTO> updateUser(@RequestParam Long id,@RequestBody UserDTO userdto){
       UserDTO userProfile = userservice.updateUserProfile(userdto, id);
       return new ResponseEntity<>(userProfile,HttpStatus.OK);
   }
   @DeleteMapping("/remove/{id}")
   public ResponseEntity<String> removeuser(@PathVariable Long id){
       String removedUser = userservice.removeUser(id);
       return new ResponseEntity<>(removedUser,HttpStatus.OK);
   }
   @GetMapping("/book/getallbooks")
   public ResponseEntity<List<BookDTO>> getAllBooks(){
       List<BookDTO> allBooks = bookService.getAllBooks();
       return new ResponseEntity<>(allBooks,HttpStatus.OK);
   }
   @GetMapping("/search")
   public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam(required = false) String title,
                                                    @RequestParam(required = false) String author,
                                                    @RequestParam(required = false) String category,
                                                    @RequestParam(required = false) String isbn,
                                                    @RequestParam(required = false) BookStatus status){
       List<BookDTO> bookDTOList = bookService.searchBooks(title, author, category, isbn, status);
       return new ResponseEntity<>(bookDTOList,HttpStatus.FOUND);
   }
@PostMapping("/{userId}/borrow")
   public ResponseEntity<BorrowRecordDTO> BorrowRequest(@PathVariable Long userId, @RequestParam Long bookId){
       BorrowRecordDTO borrowRecordDTO = borrowrecordservice.borrowBook(userId, bookId);
       return  ResponseEntity.ok(borrowRecordDTO);
   }
}
