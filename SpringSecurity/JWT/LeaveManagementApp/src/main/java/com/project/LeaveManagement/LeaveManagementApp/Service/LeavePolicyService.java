package com.project.LeaveManagement.LeaveManagementApp.Service;

import com.project.LeaveManagement.LeaveManagementApp.DTO.LeavePolicyDTO;
import com.project.LeaveManagement.LeaveManagementApp.Entity.LeavePolicy;
import com.project.LeaveManagement.LeaveManagementApp.Repository.LeavePolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeavePolicyService {

    @Autowired
    private LeavePolicyRepository leavePolicyRepository;

    public List<LeavePolicy> getAllPolicy() {
        return leavePolicyRepository.findAll();
    }

    public LeavePolicy updatePolicy(LeavePolicyDTO leavePolicyDTO) {

        LeavePolicy leavePolicy = leavePolicyRepository.findById(1L)
                .orElseThrow(()-> new RuntimeException("Leave Policy ID=1 not found!"));

        leavePolicy.setMonthlyLeave(leavePolicyDTO.getMonthlyLeave());

        return leavePolicyRepository.save(leavePolicy);
    }
}
