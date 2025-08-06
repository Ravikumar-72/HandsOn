package com.project.FinWallet.service;

import com.project.FinWallet.dto.request.ExpenseRequest;
import com.project.FinWallet.dto.response.ExpenseResponse;
import com.project.FinWallet.dto.response.ResponseDto;
import com.project.FinWallet.entity.Expense;
import com.project.FinWallet.entity.UserProfile;
import com.project.FinWallet.exception.ExpenseNotFoundException;
import com.project.FinWallet.exception.InsufficientBalanceException;
import com.project.FinWallet.exception.UserNotAuthenticatedException;
import com.project.FinWallet.mapper.ExpenseMapper;
import com.project.FinWallet.repository.ExpenseRepository;
import com.project.FinWallet.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<ExpenseResponse> getAllExpenseList() {
        return expenseRepository.findAllByUser_Id(userProfileService.getCurrentUser().getId())
                .stream()
                .map(ExpenseMapper::mapToExpenseDto)
                .collect(Collectors.toList());
    }

    public ExpenseResponse getExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(
                ()->new ExpenseNotFoundException("Expense","expenseId",Long.toString(expenseId))
        );
        return ExpenseMapper.mapToExpenseDto(expense);
    }

    @Transactional
    public void createExpense(ExpenseRequest expenseRequest) {
        UserProfile user = userProfileService.getCurrentUser();
        if(expenseRequest.getAmount() > user.getBalance()){
            throw new InsufficientBalanceException("Expense","amount",Double.toString(expenseRequest.getAmount()));
        }
        user.setBalance(user.getBalance() - expenseRequest.getAmount());
        expenseRepository.save(ExpenseMapper.mapToExpense(expenseRequest,user));
    }

    @Transactional
    public ExpenseResponse updateExpense(Long expenseId, ExpenseRequest expenseRequest) {
        UserProfile user = userProfileService.getCurrentUser();
        Expense existingExpense = expenseRepository.findById(expenseId).orElseThrow(
                ()-> new ExpenseNotFoundException("Expense","expenseId",Long.toString(expenseId))
        );

        if(!existingExpense.getUser().getId().equals(user.getId())){
            throw new UserNotAuthenticatedException("You are not authenticated to edit this expense");
        }

        double existingAmount = existingExpense.getAmount();
        double newAmount = expenseRequest.getAmount();
        double delta = newAmount - existingAmount;
        double newBalance = user.getBalance() - delta;
        if(newBalance < 0){
            throw new InsufficientBalanceException("Expense","expenseId",Long.toString(expenseId));
        }
        existingExpense.setDescription(expenseRequest.getDescription());
        existingExpense.setAmount(newAmount);
        existingExpense.setCategory(expenseRequest.getCategory());
        existingExpense.setDate(expenseRequest.getDate());

        user.setBalance(newBalance);

        expenseRepository.save(existingExpense);
        userProfileRepository.save(user);
        return ExpenseMapper.mapToExpenseDto(existingExpense);
    }

    @Transactional
    public void deleteExpense(Long expenseId) {
        UserProfile user = userProfileService.getCurrentUser();
        Expense existingExpense = expenseRepository.findById(expenseId).orElseThrow(
                ()-> new ExpenseNotFoundException("Expense","expenseId",Long.toString(expenseId))
        );

        if(!existingExpense.getUser().getId().equals(user.getId())){
            throw new UserNotAuthenticatedException("You are not authenticated to edit this expense");
        }

        double expenseAmount = existingExpense.getAmount();
        double availableBalance = user.getBalance();

        user.setBalance(expenseAmount + availableBalance);

        userProfileRepository.save(user);
        expenseRepository.deleteById(expenseId);
    }
}
