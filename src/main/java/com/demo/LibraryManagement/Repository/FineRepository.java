package com.demo.LibraryManagement.Repository;

import com.demo.LibraryManagement.Entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FineRepository extends JpaRepository<Fine,Long> {
}
