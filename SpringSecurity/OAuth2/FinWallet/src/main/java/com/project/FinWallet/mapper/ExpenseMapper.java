package com.project.FinWallet.mapper;

import com.project.FinWallet.dto.request.ExpenseRequest;
import com.project.FinWallet.dto.response.ExpenseResponse;
import com.project.FinWallet.entity.Expense;
import com.project.FinWallet.entity.UserProfile;

public class ExpenseMapper {

    public static Expense mapToExpense(ExpenseRequest expenseRequest, UserProfile user){
        Expense expense = new Expense();
        expense.setAmount(expenseRequest.getAmount());
        expense.setDate(expenseRequest.getDate());
        expense.setCategory(expenseRequest.getCategory());
        expense.setDescription(expenseRequest.getDescription());
        expense.setUser(user);
        return expense;
    }

    public static ExpenseResponse mapToExpenseDto(Expense expense){
        ExpenseResponse expenseResponse = new ExpenseResponse();
        expenseResponse.setAmount(expense.getAmount());
        expenseResponse.setDate(expense.getDate());
        expenseResponse.setCategory(expense.getCategory());
        expenseResponse.setDescription(expense.getDescription());
        return expenseResponse;
    }
}
