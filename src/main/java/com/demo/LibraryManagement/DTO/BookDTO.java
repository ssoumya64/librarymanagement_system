package com.demo.LibraryManagement.DTO;

import com.demo.LibraryManagement.Entity.BorrowRecord;
import com.demo.LibraryManagement.Entity.User;
import com.demo.LibraryManagement.enums.BookStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String ISBN;
    private String category;
    private String publisher;
    private Date publicationDate;
    private int totalCopies;
    private int availableCopies;

    private BookStatus status;

    private List<BorrowRecord> borrowRecord;

    private List<User> usersWithIssuedBook;
}
