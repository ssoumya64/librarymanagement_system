package com.demo.LibraryManagement.Entity;

import com.demo.LibraryManagement.enums.BookStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

//id, title, author, ISBN, category, publisher, publicationDate, totalCopies, availableCopies, status
@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String ISBN;
    private String category;
    private String publisher;
    private Date publicationDate;
    private int totalCopies;
    private int availableCopies;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<BorrowRecord> borrowRecord;
    @ManyToMany(mappedBy = "issuedbook")
    private List<User> usersWithIssuedBook;

}
