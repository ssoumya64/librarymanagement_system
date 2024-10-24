package com.demo.LibraryManagement.Entity;

import com.demo.LibraryManagement.enums.MemberShipType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

//id, name, email, password, membershipType, issuedBooks, registrationDate.
@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private MemberShipType membershipType;
    @OneToMany(mappedBy = "user")
    private List<BorrowRecord> borrowedRecord;
    private LocalDate registrationDate;
    @ManyToMany
    @JoinTable(
            name = "user_issued_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> issuedbook;
    @OneToMany(mappedBy = "user")
    private List<Fine> fines;

}
