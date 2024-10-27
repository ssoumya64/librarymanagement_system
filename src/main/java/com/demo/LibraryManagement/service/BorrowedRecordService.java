package com.demo.LibraryManagement.service;

import com.demo.LibraryManagement.DTO.BorrowRecordDTO;

import java.util.List;

public interface BorrowedRecordService {
    public BorrowRecordDTO borrowBook(Long userId, Long bookId);

    BorrowRecordDTO returnBook(Long borrowRecordId);

    List<BorrowRecordDTO> getUserBorrowHistory(Long userId);
    BorrowRecordDTO getBorrowRecordById(Long borrowRecordid);
}
