package com.project.FinWallet.controller;

import com.project.FinWallet.dto.request.IncomeRequest;
import com.project.FinWallet.dto.response.IncomeResponse;
import com.project.FinWallet.dto.response.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.project.FinWallet.service.IncomeService;

import java.util.List;

@RestController
@RequestMapping("api/incomes")
@PreAuthorize("hasRole('USER')")
public class IncomeController{

    @Autowired
    private IncomeService incomeService;

    @GetMapping("/all")
    public ResponseEntity<List<IncomeResponse>> getAllIncomeList(){
        List<IncomeResponse> incomeResponse = incomeService.getAllIncomeList();
        return ResponseEntity.status(HttpStatus.OK).body(incomeResponse);
    }

    @GetMapping("/{incomeId}")
    public ResponseEntity<IncomeResponse> getIncome(@PathVariable Long incomeId){
        IncomeResponse incomeResponse = incomeService.getIncome(incomeId);
        return ResponseEntity.status(HttpStatus.OK).body(incomeResponse);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDto> createIncome(@RequestBody IncomeRequest incomeRequest){
        incomeService.createIncome(incomeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("201","Income created successfully!"));
    }

    @PutMapping("/{incomeId}")
    public ResponseEntity<IncomeResponse> updateIncome(@PathVariable Long incomeId, @RequestBody IncomeRequest incomeRequest){
        IncomeResponse incomeResponse = incomeService.updateIncome(incomeId,incomeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(incomeResponse);
    }

    @DeleteMapping("/{incomeId}")
    public ResponseEntity<ResponseDto> deleteIncome(@PathVariable Long incomeId){
        incomeService.deleteIncome(incomeId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("200","Income deleted successfully!"));
    }
}
