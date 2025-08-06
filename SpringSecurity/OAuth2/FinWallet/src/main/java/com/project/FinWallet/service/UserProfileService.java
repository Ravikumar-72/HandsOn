package com.project.FinWallet.service;

import com.project.FinWallet.dto.response.BalanceResponse;
import com.project.FinWallet.dto.response.UserProfileResponse;
import com.project.FinWallet.entity.UserProfile;
import com.project.FinWallet.exception.UserNotAuthenticatedException;
import com.project.FinWallet.exception.UserProfileNotFoundException;
import com.project.FinWallet.mapper.UserProfileMapper;
import com.project.FinWallet.repository.ExpenseRepository;
import com.project.FinWallet.repository.IncomeRepository;
import com.project.FinWallet.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    public UserProfile getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            throw new UserNotAuthenticatedException("User not authenticated!");
        }
        Object principal = authentication.getPrincipal();
        String email;

        if (principal instanceof OAuth2User oAuth2User) {
            email = oAuth2User.getAttribute("email");
        }else if (principal instanceof String str) {
            email = str;
        }else if (principal instanceof UserProfile userProfile) {
            email = userProfile.getEmail();
        }else {
            throw new IllegalStateException("Unsupported principal type: " + principal.getClass().getName());
        }

        return userProfileRepository.findByEmail(email).orElseThrow(
                ()-> new UserProfileNotFoundException("Userprofile","email",email)
        );
    }

    public UserProfileResponse getMyDetails() {
        UserProfile user = getCurrentUser();
        return UserProfileMapper.mapToUserProfileResponse(user);
    }


    public BalanceResponse getMyBalance() {
        UserProfile user = getCurrentUser();

        boolean expenseExistsForUser = expenseRepository.existsByUser_Id(user.getId());
        boolean incomeExistsForUser = incomeRepository.existsByUser_Id(user.getId());

        if(expenseExistsForUser && incomeExistsForUser){
            Double totalExpenses = expenseRepository.sumByUser(user.getId());
            Double totalIncomes = incomeRepository.sumByUser(user.getId());
            double availableBalance = totalIncomes - totalExpenses;

            BalanceResponse balanceResponse = new BalanceResponse();
            balanceResponse.setTotalExpense(totalExpenses);
            balanceResponse.setTotalIncome(totalIncomes);
            balanceResponse.setAvailableBalance(availableBalance);

            return balanceResponse;
        }else if(incomeExistsForUser && !expenseExistsForUser){
            BalanceResponse balanceResponse = new BalanceResponse();
            balanceResponse.setTotalExpense(0);
            balanceResponse.setTotalIncome(incomeRepository.sumByUser(user.getId()));
            balanceResponse.setAvailableBalance(incomeRepository.sumByUser(user.getId()));

            return balanceResponse;
        }else{
            BalanceResponse balanceResponse = new BalanceResponse();
            balanceResponse.setTotalExpense(0);
            balanceResponse.setTotalIncome(0);
            balanceResponse.setAvailableBalance(0);

            return balanceResponse;
        }

    }
}
