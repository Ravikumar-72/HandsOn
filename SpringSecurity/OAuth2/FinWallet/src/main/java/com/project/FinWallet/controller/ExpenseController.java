package com.project.FinWallet.controller;

import com.project.FinWallet.dto.request.ExpenseRequest;
import com.project.FinWallet.dto.response.ExpenseResponse;
import com.project.FinWallet.dto.response.ResponseDto;
import com.project.FinWallet.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/expenses")
@PreAuthorize("hasRole('USER')")
public class ExpenseController{

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/all")
    public ResponseEntity<List<ExpenseResponse>> getAllExpenseList(){
        List<ExpenseResponse> expenseResponses = expenseService.getAllExpenseList();
        return ResponseEntity.status(HttpStatus.OK).body(expenseResponses);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> getExpense(@PathVariable Long expenseId){
        ExpenseResponse expenseResponse = expenseService.getExpense(expenseId);
        return ResponseEntity.status(HttpStatus.OK).body(expenseResponse);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDto> createExpense(@RequestBody ExpenseRequest expenseRequest){
        expenseService.createExpense(expenseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("201","Expense created successfully!"));
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Long expenseId, @RequestBody ExpenseRequest expenseRequest){
        ExpenseResponse expenseResponse = expenseService.updateExpense(expenseId,expenseRequest);
        return ResponseEntity.status(HttpStatus.OK).body(expenseResponse);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<ResponseDto> deleteExpense(@PathVariable Long expenseId){
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("200","Expense deleted successfully!"));
    }
}
