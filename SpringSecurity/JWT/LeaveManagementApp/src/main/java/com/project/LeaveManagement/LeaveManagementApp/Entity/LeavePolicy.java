package com.project.LeaveManagement.LeaveManagementApp.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class LeavePolicy {

    @Id
    private Long id;
    private Double monthlyLeave;

}
