package com.demo.LibraryManagement.service;

import com.demo.LibraryManagement.DTO.BookDTO;
import com.demo.LibraryManagement.enums.BookStatus;

import java.util.List;

public interface BookService {
    public BookDTO createbooks(BookDTO book);

    public List<BookDTO> getAllBooks();

    BookDTO getBookById(Long bookid);

    List<BookDTO> searchBooks(String title, String author, String category, String isbn, BookStatus status);
}
