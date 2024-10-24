package com.demo.LibraryManagement.Entity;

import com.demo.LibraryManagement.enums.FineStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="fine")
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "borrow_record_id",nullable = false)
    private BorrowRecord borrowrecord;
    private Double amount;
    private LocalDate issueDate;
    private LocalDate paidDate;
    @Enumerated(EnumType.STRING)
    private FineStatus finsestatus;
}
