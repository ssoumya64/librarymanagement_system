package com.demo.LibraryManagement.controller;

import com.demo.LibraryManagement.DTO.BorrowRecordDTO;
import com.demo.LibraryManagement.DTO.LibrarianDTO;
import com.demo.LibraryManagement.Entity.BorrowRecord;
import com.demo.LibraryManagement.Entity.Fine;
import com.demo.LibraryManagement.Entity.Librarian;
import com.demo.LibraryManagement.service.BorrowedRecordService;
import com.demo.LibraryManagement.service.FineService;
import com.demo.LibraryManagement.service.LibrarianService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/librarian")
public class LibrarianController {

    private final LibrarianService librarianService;
    private final FineService fineservice;

    private final BorrowedRecordService borrowRecordService;

    public LibrarianController(LibrarianService librarianService, FineService fineservice, BorrowedRecordService borrowRecordService) {
        this.librarianService = librarianService;
        this.fineservice = fineservice;
        this.borrowRecordService = borrowRecordService;
    }

    @PostMapping("/register")
    public ResponseEntity<Librarian> registerLibrarian(@Valid @RequestBody LibrarianDTO registrationDTO) {
        Librarian newLibrarian = librarianService.registerLibrarian(registrationDTO);
        return ResponseEntity.ok(newLibrarian);
    }

    @PostMapping("/deactivate/{userId}")
    public ResponseEntity<String> deactivateUser(@PathVariable Long userId) {
        String message = librarianService.deactivateUser(userId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/fines/{userId}")
    public ResponseEntity<List<Fine>> getUnpaidFines(@PathVariable Long userId) {
        List<Fine> unpaidFines = fineservice.getFineByUser(userId);
        return ResponseEntity.ok(unpaidFines);
    }

    @GetMapping("/borrow-records/{userId}")
    public ResponseEntity<List<BorrowRecordDTO>> getBorrowedRecordsByUser(@PathVariable Long userId) {
        List<BorrowRecordDTO> userBorrowHistory = borrowRecordService.getUserBorrowHistory(userId);
        return ResponseEntity.ok(userBorrowHistory);
    }

    @PostMapping("/create-fine")
    public ResponseEntity<Fine> createFine(@RequestBody Fine fine) {
        Fine createdFine = fineservice.createFine(fine.getUser(), fine.getBorrowrecord(), fine.getPaidDate());
        return ResponseEntity.ok(createdFine);
    }

    @PutMapping("/pay-fine/{fineId}")
    public ResponseEntity<Fine> payFine(@PathVariable Long fineId) {
        Fine paidFine = fineservice.payFine(fineId);
        return ResponseEntity.ok(paidFine);
    }
}
