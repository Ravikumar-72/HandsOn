package com.project.FinWallet.mapper;

import com.project.FinWallet.dto.request.IncomeRequest;
import com.project.FinWallet.dto.response.IncomeResponse;
import com.project.FinWallet.entity.Income;
import com.project.FinWallet.entity.UserProfile;

public class IncomeMapper {

    public static Income mapToIncome(IncomeRequest request, UserProfile user){
        Income income = new Income();
        income.setAmount(request.getAmount());
        income.setSource(request.getSource());
        income.setDateReceived(request.getDateReceived());
        income.setUser(user);
        return income;
    }

    public static IncomeResponse mapToIncomeDto(Income income){
        IncomeResponse incomeResponse = new IncomeResponse();
        incomeResponse.setAmount(income.getAmount());
        incomeResponse.setSource(income.getSource());
        incomeResponse.setDateReceived(income.getDateReceived());
        return incomeResponse;
    }
}
