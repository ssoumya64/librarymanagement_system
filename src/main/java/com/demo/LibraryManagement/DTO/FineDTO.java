package com.demo.LibraryManagement.DTO;

import com.demo.LibraryManagement.Entity.BorrowRecord;
import com.demo.LibraryManagement.Entity.User;
import com.demo.LibraryManagement.enums.FineStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class FineDTO {
    private Long id;

    private User user;

    private BorrowRecord borrowrecord;
    private Double amount;
    private LocalDate issueDate;
    private LocalDate paidDate;

    private FineStatus finsestatus;
}
