package com.demo.LibraryManagement.service.impl;

import com.demo.LibraryManagement.DTO.BorrowRecordDTO;
import com.demo.LibraryManagement.DTO.FineDTO;
import com.demo.LibraryManagement.Entity.BorrowRecord;
import com.demo.LibraryManagement.Entity.Fine;
import com.demo.LibraryManagement.Entity.User;
import com.demo.LibraryManagement.Exception.ResourceNotFoundException;
import com.demo.LibraryManagement.Repository.FineRepository;
import com.demo.LibraryManagement.enums.FineStatus;
import com.demo.LibraryManagement.service.BorrowedRecordService;
import com.demo.LibraryManagement.service.FineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FineServiceImpl implements FineService {
    private final FineRepository finerepository;

    private final ModelMapper modelmapper;

    private static final double DAILY_FINE_RATE = 10.0; //per day fine

    public FineServiceImpl(FineRepository finerepository, ModelMapper modelmapper) {
        this.finerepository = finerepository;

        this.modelmapper = modelmapper;
    }
    @Override
    public Fine createFine(User user, BorrowRecord borrowrecordDTO, LocalDate dueDate) {
        BorrowRecord borrowrecord = modelmapper.map(borrowrecordDTO, BorrowRecord.class);
        long overDueDay = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        if (overDueDay < 0) {
            return null;//No fine needed

        }
        double fineAmount = calculateFineAmount(overDueDay);
        Fine fine = new Fine();
        fine.setUser(user);
        fine.setBorrowrecord(borrowrecord);
        fine.setAmount(fineAmount);
        fine.setIssueDate(LocalDate.now());
        fine.setFinsestatus(FineStatus.UNPAID);
        return finerepository.save(fine);
    }

    private double calculateFineAmount(long overDueDay) {
        return overDueDay*DAILY_FINE_RATE;
    }
   @Override
    public Fine payFine(Long fineId){
       Fine fine = finerepository.findById(fineId).orElseThrow(() -> new ResourceNotFoundException("No Fine id is found"));
       fine.setFinsestatus(FineStatus.PAID);
       fine.setPaidDate(LocalDate.now());
       return finerepository.save(fine);
    }
    @Override
    public List<Fine> getFineByUser(Long userId){
        List<Fine> byUserId = finerepository.findByUserId(userId);
        return byUserId;
    }
    @Override
    public List<Fine> getFineByBorrowRecord(Long borrowRecordId){
        return finerepository.findByBorrowrecordId(borrowRecordId);
    }
    @Override
    public List<FineDTO> getUnpaidFines(Long userId) {
        List<FineDTO> unpaidFines = finerepository.findByUserIdAndFinsestatus(userId, FineStatus.UNPAID);
        return unpaidFines.stream()
                .map(fine -> modelmapper.map(fine, FineDTO.class)) // Assuming you have a FineDTO to map the Fine entity
                .collect(Collectors.toList());
    }

}
