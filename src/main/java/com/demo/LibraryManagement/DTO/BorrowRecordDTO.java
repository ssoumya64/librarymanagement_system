package com.demo.LibraryManagement.DTO;

import com.demo.LibraryManagement.Entity.Book;
import com.demo.LibraryManagement.Entity.BorrowRecord;
import com.demo.LibraryManagement.Entity.Fine;
import com.demo.LibraryManagement.Entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecordDTO {
    private Long id;

    private User user;

    private Book book;
    private LocalDate borrowdate;
    private LocalDate returndate;

    private List<Fine> fines;


}
