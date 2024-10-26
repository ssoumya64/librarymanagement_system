package com.demo.LibraryManagement.Repository;

import com.demo.LibraryManagement.Entity.Book;
import com.demo.LibraryManagement.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
     Book findByTitle(String title);
     List<Book> findByAuthor(String author);
     @Query(value = "SELECT * FROM library_management.book WHERE " +
             "(:title IS NULL OR title LIKE CONCAT('%', :title, '%')) AND " +
             "(:author IS NULL OR author LIKE CONCAT('%', :author, '%')) AND " +
             "(:category IS NULL OR category = :category) AND " +
             "(:isbn IS NULL OR isbn = :isbn) AND " +
             "(:status IS NULL OR status = :status)",
             nativeQuery = true)

     List<Book> BookSearch(@Param("title") String title,
                           @Param("author") String author,
                           @Param("category") String category,
                           @Param("isbn") String isbn,
                           @Param("status") BookStatus status
                           );
}
