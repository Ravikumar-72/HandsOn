package com.project.FinWallet.controller;

import com.project.FinWallet.dto.response.BalanceResponse;
import com.project.FinWallet.dto.response.UserProfileResponse;
import com.project.FinWallet.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@PreAuthorize("hasRole('USER')")
public class UserProfileController{

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyDetails(){
        UserProfileResponse userProfileResponse = userProfileService.getMyDetails();
        return ResponseEntity.status(HttpStatus.OK).body(userProfileResponse);
    }
    // GET /balance

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getMyBalance(){
        BalanceResponse balanceResponse = userProfileService.getMyBalance();
        return ResponseEntity.status(HttpStatus.OK).body(balanceResponse);
    }

}
