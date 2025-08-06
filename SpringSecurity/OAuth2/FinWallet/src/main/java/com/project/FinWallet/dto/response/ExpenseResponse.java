package com.project.FinWallet.dto.response;

import com.project.FinWallet.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpenseResponse {

    private String description;
    private double amount;
    private LocalDate date;
    private Category category;
}
