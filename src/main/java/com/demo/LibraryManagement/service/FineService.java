package com.demo.LibraryManagement.service;

import com.demo.LibraryManagement.DTO.BorrowRecordDTO;
import com.demo.LibraryManagement.DTO.FineDTO;
import com.demo.LibraryManagement.DTO.UserDTO;
import com.demo.LibraryManagement.Entity.BorrowRecord;
import com.demo.LibraryManagement.Entity.Fine;
import com.demo.LibraryManagement.Entity.User;

import java.time.LocalDate;
import java.util.List;

public interface FineService {
    Fine createFine(User user, BorrowRecord borrowrecord, LocalDate dueDate);
    Fine payFine(Long fineId);
    List<Fine> getFineByUser(Long userId);
    List<Fine> getFineByBorrowRecord(Long borrowRecordId);
    List<FineDTO> getUnpaidFines(Long userId);
}
