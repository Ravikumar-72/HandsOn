package com.project.FinWallet.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IncomeResponse {

    private double amount;
    private String source;
    private LocalDate dateReceived;
}
