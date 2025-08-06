package com.project.FinWallet.dto.response;

import lombok.Data;

@Data
public class BalanceResponse {

    private double totalIncome;
    private double totalExpense;
    private double availableBalance;
}
