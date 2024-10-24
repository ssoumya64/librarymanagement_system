package com.demo.LibraryManagement.Repository;

import com.demo.LibraryManagement.Entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorroewRecordrepository extends JpaRepository<BorrowRecord,Long> {
}
