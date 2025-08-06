package com.project.LeaveManagement.LeaveManagementApp.Service;

import com.project.LeaveManagement.LeaveManagementApp.Entity.LeavePolicy;
import com.project.LeaveManagement.LeaveManagementApp.Entity.User;
import com.project.LeaveManagement.LeaveManagementApp.Repository.LeavePolicyRepository;
import com.project.LeaveManagement.LeaveManagementApp.Repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Data
public class AddMonthlyLeaveService {

    @Autowired
    private LeavePolicyRepository leavePolicyRepository;
    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 1 1 * ?") //runs at 1AM on 1st of each month
    public void addMonthlyLeaves(){
        LeavePolicy leavePolicy = leavePolicyRepository.findById(1L)
                .orElseThrow(()->new RuntimeException("Leave policy not found"));

        List<User> users = userRepository.findAll();
        for(User user : users){
            user.setLeaveBalance(user.getLeaveBalance() + leavePolicy.getMonthlyLeave());
            user.setLastLeaveCredited(LocalDate.now());
        }

        userRepository.saveAll(users);
    }

}
