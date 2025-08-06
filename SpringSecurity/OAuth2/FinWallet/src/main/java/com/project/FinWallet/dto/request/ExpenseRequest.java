package com.project.FinWallet.dto.request;

import com.project.FinWallet.entity.Category;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpenseRequest {

    private String description;
    private double amount;
    private LocalDate date;
    private Category category;
}
