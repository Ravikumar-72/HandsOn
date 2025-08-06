package com.project.FinWallet.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IncomeRequest {

    private double amount;
    private String source;
    private LocalDate dateReceived;
}
