package com.demo.LibraryManagement.service.impl;

import com.demo.LibraryManagement.DTO.BookDTO;
import com.demo.LibraryManagement.Entity.Book;
import com.demo.LibraryManagement.Exception.CopiesNotAvailableOrExceedException;
import com.demo.LibraryManagement.Exception.ResourceNotFoundException;
import com.demo.LibraryManagement.Repository.BookRepository;
import com.demo.LibraryManagement.enums.BookStatus;
import com.demo.LibraryManagement.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class BookServiceImpl implements BookService {


    private final BookRepository bookrepository;

    private final ModelMapper modelmapper;

    public BookServiceImpl(BookRepository bookrepository, ModelMapper modelmapper) {
        this.bookrepository = bookrepository;
        this.modelmapper = modelmapper;
    }

    @Override
    public BookDTO createbooks(BookDTO book) {

        Book bookentity = modelmapper.map(book, Book.class);
        Book savebook = bookrepository.save(bookentity);
        BookDTO map = modelmapper.map(savebook, BookDTO.class);
        return map;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> allbooks = bookrepository.findAll();
        List<BookDTO> collect = allbooks.stream().map(bookentity -> modelmapper.map(bookentity, BookDTO.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public BookDTO getBookById(Long bookid) {
        Optional<Book> byId = isBookExist(bookid);
        if(byId.isPresent()){
            Book book = byId.get();
            BookDTO map = modelmapper.map(book, BookDTO.class);
            return map;
        }else{
           throw new ResourceNotFoundException("Book With Given id is not found");
        }

    }

    private Optional<Book> isBookExist(Long bookid) {
       return bookrepository.findById(bookid);
    }


    @Override
    public List<BookDTO> searchBooks(String title, String author, String category, String isbn, BookStatus status) {
        List<Book> books = bookrepository.BookSearch(title, author, category, isbn, status);
        List<BookDTO> collect = books.stream().map(bookEntity -> modelmapper.map(bookEntity, BookDTO.class)).collect(Collectors.toList());
        return collect;
    }
    @Override
    public void decreaseavailablecopies(Long bookid){
        Optional<Book> bookExist = isBookExist(bookid);
        if(bookExist.isPresent()){
            Book book = bookExist.get();
            if(book.getAvailableCopies()>0){
                book.setAvailableCopies(book.getAvailableCopies()-1);
                bookrepository.save(book);
            }else{
                throw new CopiesNotAvailableOrExceedException("No copies available");
            }
        }else {
            throw new ResourceNotFoundException("No books available with given copies");
        }
    }

    @Override
    public void increaseavailablecopies(Long bookid) {
        Optional<Book> bookExist = isBookExist(bookid);
        if(bookExist.isPresent()){
            Book book = bookExist.get();
            if(book.getAvailableCopies()<book.getTotalCopies()){
                book.setAvailableCopies(book.getAvailableCopies()+1);
                bookrepository.save(book);
            }else{
                throw new CopiesNotAvailableOrExceedException("Can not exceed total Copies");
            }
        }else {
            throw new ResourceNotFoundException("No books available with given copies");
        }
    }


}

