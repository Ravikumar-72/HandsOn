package com.project.LeaveManagement.LeaveManagementApp.DTO;

import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String username;
    private String password;
    private String email;
}
