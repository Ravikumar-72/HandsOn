package com.project.FinWallet.service;

import com.project.FinWallet.dto.request.IncomeRequest;
import com.project.FinWallet.dto.response.IncomeResponse;
import com.project.FinWallet.entity.Expense;
import com.project.FinWallet.entity.Income;
import com.project.FinWallet.entity.UserProfile;
import com.project.FinWallet.exception.ExpenseNotFoundException;
import com.project.FinWallet.exception.InsufficientBalanceException;
import com.project.FinWallet.exception.UserNotAuthenticatedException;
import com.project.FinWallet.mapper.ExpenseMapper;
import com.project.FinWallet.mapper.IncomeMapper;
import com.project.FinWallet.repository.IncomeRepository;
import com.project.FinWallet.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private IncomeRepository incomeRepository;

    public List<IncomeResponse> getAllIncomeList() {
        return incomeRepository.findAllByUser_Id(userProfileService.getCurrentUser().getId())
                .stream()
                .map(IncomeMapper::mapToIncomeDto)
                .collect(Collectors.toList());
    }

    public IncomeResponse getIncome(Long incomeId) {
        Income income = incomeRepository.findById(incomeId).orElseThrow(
                ()->new ExpenseNotFoundException("Income","incomeId",Long.toString(incomeId))
        );
        return IncomeMapper.mapToIncomeDto(income);
    }

    @Transactional
    public void createIncome(IncomeRequest incomeRequest) {
        UserProfile user = userProfileService.getCurrentUser();
        double totalBalance = user.getBalance() + incomeRequest.getAmount();
        user.setBalance(totalBalance);
        incomeRepository.save(IncomeMapper.mapToIncome(incomeRequest,user));
    }

    @Transactional
    public IncomeResponse updateIncome(Long incomeId, IncomeRequest incomeRequest) {
        UserProfile user = userProfileService.getCurrentUser();
        Income existingIncome = incomeRepository.findById(incomeId).orElseThrow(
                ()-> new ExpenseNotFoundException("Income","incomeId",Long.toString(incomeId))
        );

        if(!existingIncome.getUser().getId().equals(user.getId())){
            throw new UserNotAuthenticatedException("You are not authenticated to edit this income");
        }

        double userBalance = user.getBalance();
        double oldIncome = existingIncome.getAmount();
        double newBalance = userBalance - oldIncome;
        if(newBalance < 0){
            user.setBalance(0);
        }

        existingIncome.setAmount(newBalance);
        existingIncome.setSource(incomeRequest.getSource());
        existingIncome.setDateReceived(incomeRequest.getDateReceived());

        user.setBalance(newBalance);

        incomeRepository.save(existingIncome);
        userProfileRepository.save(user);
        return IncomeMapper.mapToIncomeDto(existingIncome);
    }

    @Transactional
    public void deleteIncome(Long incomeId) {
        UserProfile user = userProfileService.getCurrentUser();
        Income existingIncome = incomeRepository.findById(incomeId).orElseThrow(
                ()-> new ExpenseNotFoundException("Income","incomeId",Long.toString(incomeId))
        );

        if(!existingIncome.getUser().getId().equals(user.getId())){
            throw new UserNotAuthenticatedException("You are not authenticated to edit this income");
        }

        double incomeAmount = existingIncome.getAmount();
        double availableBalance = user.getBalance();
        double newBalance = availableBalance - incomeAmount;
        if(newBalance < 0){
            user.setBalance(0);
        }

        user.setBalance(newBalance);

        userProfileRepository.save(user);
        incomeRepository.deleteById(incomeId);
    }
}
