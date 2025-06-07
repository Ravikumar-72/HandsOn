package com.project.LeaveManagement.LeaveManagementApp.DTO;

import com.project.LeaveManagement.LeaveManagementApp.Entity.LeaveStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LeaveStatus status;
    private String managerComment;
}
