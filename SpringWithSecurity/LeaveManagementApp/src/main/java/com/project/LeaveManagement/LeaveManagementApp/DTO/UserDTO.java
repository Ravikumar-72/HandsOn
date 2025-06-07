package com.project.LeaveManagement.LeaveManagementApp.DTO;


import lombok.Data;

@Data
public class UserDTO {

    private String username;
    private String email;
    private Double leaveBalance;
    private Long managerId;

}
