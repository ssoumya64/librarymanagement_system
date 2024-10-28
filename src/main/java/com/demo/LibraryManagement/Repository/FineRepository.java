package com.demo.LibraryManagement.Repository;

import com.demo.LibraryManagement.Entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine,Long> {
    List<Fine> findByUserId(Long userId);
    List<Fine> findByBorrowrecordId(Long borrowRecordId);
}
