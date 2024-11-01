package com.demo.LibraryManagement.controller;

import com.demo.LibraryManagement.DTO.BorrowRecordDTO;
import com.demo.LibraryManagement.DTO.FineDTO;
import com.demo.LibraryManagement.DTO.UserDTO;
import com.demo.LibraryManagement.Entity.BorrowRecord;
import com.demo.LibraryManagement.Entity.Fine;
import com.demo.LibraryManagement.Entity.User;
import com.demo.LibraryManagement.service.FineService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fine")
public class FineController {
    private final FineService fineservice;

    private final ModelMapper modelmapper;

    public FineController(FineService fineservice, ModelMapper modelmapper) {
        this.fineservice = fineservice;
        this.modelmapper = modelmapper;
    }
    @PostMapping("/create")
    public ResponseEntity<FineDTO> createFine(@RequestParam User user, @RequestParam BorrowRecordDTO borrowrecord, @RequestParam String dueDate){
        LocalDate parse = LocalDate.parse(dueDate);
        BorrowRecord borrowRecord = modelmapper.map(borrowrecord, BorrowRecord.class);
        Fine fine = fineservice.createFine(user, borrowRecord, parse);
        if(fine==null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        FineDTO fineDTO = modelmapper.map(fine, FineDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(fineDTO);
    }
    @GetMapping("/{fineId}/pay")
    public ResponseEntity<FineDTO> payFine(@PathVariable Long fineId){
    Fine fine = fineservice.payFine(fineId);
    FineDTO fineDTO = modelmapper.map(fine, FineDTO.class);
    return ResponseEntity.ok(fineDTO);
}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FineDTO>> getFinesByUser(@PathVariable Long userId){
        List<Fine> fineByUser = fineservice.getFineByUser(userId);
        List<FineDTO> collect = fineByUser.stream().map(e -> modelmapper.map(e, FineDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }
    @GetMapping("/borrowRecord/{borrowRecordId}")
    public ResponseEntity<List<FineDTO>> getFinesByBorrowRecord(@PathVariable Long borrowRecordId) {
        List<Fine> fineByBorrowRecord = fineservice.getFineByBorrowRecord(borrowRecordId);
        List<FineDTO> collect = fineByBorrowRecord.stream().map(k -> modelmapper.map(k, FineDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

}
