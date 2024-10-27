package com.demo.LibraryManagement.controller;

import com.demo.LibraryManagement.DTO.BorrowRecordDTO;
import com.demo.LibraryManagement.service.BorrowedRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowRecord")
public class BorrowRecordController {
    private final BorrowedRecordService borrowedreordservice;

    public BorrowRecordController(BorrowedRecordService borrowedreordservice) {
        this.borrowedreordservice = borrowedreordservice;
    }
    @PostMapping("/borrow")
    public ResponseEntity<BorrowRecordDTO> borrowBook(@RequestParam Long userId,@RequestParam Long bookId){
        BorrowRecordDTO borrowRecordDTO = borrowedreordservice.borrowBook(userId, bookId);
        return  ResponseEntity.ok(borrowRecordDTO);
    }
    @GetMapping("/return")
    public ResponseEntity<BorrowRecordDTO> returnBook(Long borrowRecordId){
        BorrowRecordDTO borrowRecordDTO = borrowedreordservice.returnBook(borrowRecordId);
        return ResponseEntity.ok(borrowRecordDTO);
    }
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<BorrowRecordDTO>> getUserBorrowHistory(@PathVariable Long userId){
        List<BorrowRecordDTO> userBorrowHistory = borrowedreordservice.getUserBorrowHistory(userId);
        return ResponseEntity.ok(userBorrowHistory);
    }
   @GetMapping("/{id}")
    public ResponseEntity<BorrowRecordDTO> getBorrowRecord(@PathVariable Long id){
       BorrowRecordDTO borrowRecordById = borrowedreordservice.getBorrowRecordById(id);
       return ResponseEntity.ok(borrowRecordById);
   }

}
