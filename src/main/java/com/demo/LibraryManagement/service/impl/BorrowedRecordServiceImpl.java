package com.demo.LibraryManagement.service.impl;

import com.demo.LibraryManagement.DTO.BorrowRecordDTO;
import com.demo.LibraryManagement.Entity.Book;
import com.demo.LibraryManagement.Entity.BorrowRecord;
import com.demo.LibraryManagement.Entity.Fine;
import com.demo.LibraryManagement.Entity.User;
import com.demo.LibraryManagement.Exception.CopiesNotAvailableOrExceedException;
import com.demo.LibraryManagement.Exception.ResourceNotFoundException;
import com.demo.LibraryManagement.Repository.BookRepository;
import com.demo.LibraryManagement.Repository.BorroewRecordrepository;
import com.demo.LibraryManagement.Repository.FineRepository;
import com.demo.LibraryManagement.Repository.UserRepository;
import com.demo.LibraryManagement.enums.FineStatus;
import com.demo.LibraryManagement.service.BookService;
import com.demo.LibraryManagement.service.BorrowedRecord;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

public class BorrowedRecordServiceImpl implements BorrowedRecord {
    private final BookRepository bookrepository;
    private final UserRepository userrepository;
    private final BorroewRecordrepository borrowrecordrepository;
    private final BookService bookservice;

    private final FineRepository fineRepository;

    private final ModelMapper modelmapper;

    private static final int MAX_BORROW_DAYS=14;
    private static final double DAILY_FINE_RATE=10; //per day fine

    public BorrowedRecordServiceImpl(BookRepository bookrepository, UserRepository userrepository, BorroewRecordrepository borrowrecordrepository, BookService bookservice, FineRepository fineRepository, ModelMapper modelmapper) {
        this.bookrepository = bookrepository;
        this.userrepository = userrepository;
        this.borrowrecordrepository = borrowrecordrepository;
        this.bookservice = bookservice;
        this.fineRepository = fineRepository;
        this.modelmapper = modelmapper;
    }

    @Override
    public BorrowRecordDTO borrowBook(Long userId, Long bookId) {
        Optional<Book> bookbyId = bookrepository.findById(bookId);
        Optional<User> userbyId = userrepository.findById(userId);
        if(bookbyId.isEmpty()|| userbyId.isEmpty()){
            throw new ResourceNotFoundException("User or Book with given id is not found");
        }
        Book book = bookbyId.get();
        if(book.getAvailableCopies()<=0){
            throw new CopiesNotAvailableOrExceedException("No more copies available");
        }
        bookservice.decreaseavailablecopies(bookId);
        BorrowRecord borrowrecord= new BorrowRecord();
        borrowrecord.setUser(userbyId.get());
        borrowrecord.setBook(book);
        borrowrecord.setBorrowdate(LocalDate.now());
        borrowrecord.setReturndate(LocalDate.now().plusDays(MAX_BORROW_DAYS));
        BorrowRecord saverecord = borrowrecordrepository.save(borrowrecord);
        BorrowRecordDTO map = modelmapper.map(saverecord, BorrowRecordDTO.class);
        return map;
    }

    @Override
    public BorrowRecordDTO returnBook(Long userId, Long bookId) {
        Optional<BorrowRecord> borrowRecordOpt  = borrowrecordrepository.findByUserIdAndBookIdAndReturndateIsNull(userId, bookId);
        if(borrowRecordOpt.isEmpty()){
         throw new ResourceNotFoundException(" No active borrow record found for this user and book");
        }
        BorrowRecord borrowRecord = borrowRecordOpt.get();
        LocalDate currentreturndate = LocalDate.now();
        LocalDate dueDate = borrowRecord.getBorrowdate().plusWeeks(2);
       if(currentreturndate.isAfter(dueDate)){
           long delayday = DAYS.between(dueDate, currentreturndate);
           double fineAmount = delayday * DAILY_FINE_RATE;

           Fine fine=new Fine();
           fine.setUser(borrowRecord.getUser());
           fine.setBorrowrecord(borrowRecord);
           fine.setAmount(fineAmount);
           fine.setIssueDate(currentreturndate);
           fine.setFinsestatus(FineStatus.UNPAID);

           fineRepository.save(fine);

           borrowRecord.getFines().add(fine);
       }

        Book book = borrowRecord.getBook();
       book.setAvailableCopies(book.getAvailableCopies()+1);
       bookrepository.save(book);

       borrowRecord.setReturndate(currentreturndate);
        BorrowRecord saveborrowrecord = borrowrecordrepository.save(borrowRecord);
        BorrowRecordDTO map = modelmapper.map(saveborrowrecord, BorrowRecordDTO.class);
        return map;
    }
}
