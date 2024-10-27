package com.demo.LibraryManagement.service;

import com.demo.LibraryManagement.DTO.BorrowRecordDTO;

public interface BorrowedRecord {
    public BorrowRecordDTO borrowBook(Long userId, Long bookId);
    public BorrowRecordDTO returnBook(Long userId, Long bookId);
}
