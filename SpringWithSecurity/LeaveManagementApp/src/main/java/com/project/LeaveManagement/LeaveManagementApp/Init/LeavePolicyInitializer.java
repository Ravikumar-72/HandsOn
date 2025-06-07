package com.project.LeaveManagement.LeaveManagementApp.Init;

import com.project.LeaveManagement.LeaveManagementApp.Entity.LeavePolicy;
import com.project.LeaveManagement.LeaveManagementApp.Repository.LeavePolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeavePolicyInitializer implements ApplicationRunner {

    @Autowired
    private LeavePolicyRepository leavePolicyRepository;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(!leavePolicyRepository.existsById(1L)){
            LeavePolicy policy = new LeavePolicy();
            policy.setId(1L);
            policy.setMonthlyLeave(1.5);
            
            leavePolicyRepository.save(policy);
        }
    }
}
