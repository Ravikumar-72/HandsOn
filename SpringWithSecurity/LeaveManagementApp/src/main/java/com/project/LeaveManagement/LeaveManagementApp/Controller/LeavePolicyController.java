package com.project.LeaveManagement.LeaveManagementApp.Controller;

import com.project.LeaveManagement.LeaveManagementApp.DTO.LeavePolicyDTO;
import com.project.LeaveManagement.LeaveManagementApp.Entity.LeavePolicy;
import com.project.LeaveManagement.LeaveManagementApp.Service.LeavePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policy")
@PreAuthorize("hasRole('ADMIN')")
public class LeavePolicyController {

    @Autowired
    private LeavePolicyService leavePolicyService;

    @GetMapping("/all")
    public ResponseEntity<List<LeavePolicy>> getAllPolicy(){
        return ResponseEntity.ok(leavePolicyService.getAllPolicy());
    }

    @PutMapping("/update")
    public ResponseEntity<LeavePolicy> updatePolicy(@RequestBody LeavePolicyDTO leavePolicyDTO){
        return ResponseEntity.ok(leavePolicyService.updatePolicy(leavePolicyDTO));
    }

}
