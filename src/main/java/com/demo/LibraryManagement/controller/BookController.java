package com.demo.LibraryManagement.controller;

import com.demo.LibraryManagement.DTO.BookDTO;
import com.demo.LibraryManagement.Exception.ResourceNotFoundException;
import com.demo.LibraryManagement.Repository.BookRepository;
import com.demo.LibraryManagement.enums.BookStatus;
import com.demo.LibraryManagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookservice;

    public BookController(BookService bookservice) {
        this.bookservice = bookservice;
    }


    @PostMapping("/ebooks")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO book){
        BookDTO createbooks = bookservice.createbooks(book);
        return new ResponseEntity<>(createbooks,HttpStatus.CREATED);

    }
    @GetMapping("/allbook")
    public ResponseEntity<List<BookDTO>> getAllBooks(){
        List<BookDTO> allBooks = bookservice.getAllBooks();
        return new ResponseEntity<>(allBooks,HttpStatus.OK);
    }
    @GetMapping("/{bookid}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long bookid){
        BookDTO bookById = bookservice.getBookById(bookid);
         return new ResponseEntity<>(bookById,HttpStatus.FOUND);
    }


    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam(required = false) String title,
                                                     @RequestParam(required = false) String author,
                                                     @RequestParam(required = false) String category,
                                                     @RequestParam(required = false) String isbn,
                                                     @RequestParam(required = false) BookStatus status
                                                     ){
        List<BookDTO> bookDTOS = bookservice.searchBooks(title, author, category, isbn, status);
        return new ResponseEntity<>(bookDTOS,HttpStatus.FOUND);

    }

}
