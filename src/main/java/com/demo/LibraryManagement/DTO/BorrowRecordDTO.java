package com.demo.LibraryManagement.DTO;

import com.demo.LibraryManagement.Entity.Book;
import com.demo.LibraryManagement.Entity.Fine;
import com.demo.LibraryManagement.Entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

public class BorrowRecordDTO {
    private Long id;

    private User user;

    private Book book;
    private LocalDate borrowdate;
    private LocalDate returndate;

    private List<Fine> fines;
}