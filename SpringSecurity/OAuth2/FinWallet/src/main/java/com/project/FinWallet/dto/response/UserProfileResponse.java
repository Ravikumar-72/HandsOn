package com.project.FinWallet.dto.response;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserProfileResponse {

    private String name;
    private String email;
    private String provider;
    private double balance;
}
