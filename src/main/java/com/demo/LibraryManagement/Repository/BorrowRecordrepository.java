package com.demo.LibraryManagement.Repository;

import com.demo.LibraryManagement.Entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRecordrepository extends JpaRepository<BorrowRecord,Long> {
//    Optional<BorrowRecord> findByUserIdAndBookIdAndReturndateIsNull(Long borrowRecordId);

    List<BorrowRecord> findByUserId(Long userId);
}
