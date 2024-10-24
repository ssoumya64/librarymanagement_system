package com.demo.LibraryManagement.Repository;

import com.demo.LibraryManagement.Entity.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian,Long> {
}
