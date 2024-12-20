package com.demo.LibraryManagement.DTO;

import com.demo.LibraryManagement.Entity.Book;
import com.demo.LibraryManagement.Entity.BorrowRecord;
import com.demo.LibraryManagement.Entity.Fine;
import com.demo.LibraryManagement.enums.MemberShipType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;

    private MemberShipType membershipType;

    private List<BorrowRecord> borrowedRecord;
    private LocalDate registrationDate;

    private List<Book> issuedbook;

    private List<Fine> fines;

    private Boolean isActive;
}
