package com.project.LeaveManagement.LeaveManagementApp.Controller;

import com.project.LeaveManagement.LeaveManagementApp.DTO.ApprovalDTO;
import com.project.LeaveManagement.LeaveManagementApp.DTO.LeavePolicyDTO;
import com.project.LeaveManagement.LeaveManagementApp.DTO.LeaveRequestDTO;
import com.project.LeaveManagement.LeaveManagementApp.DTO.UserDTO;
import com.project.LeaveManagement.LeaveManagementApp.Entity.LeaveRequest;
import com.project.LeaveManagement.LeaveManagementApp.Service.LeaveRequestService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping("/apply")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER')")
    public ResponseEntity<LeaveRequest> applyLeave(@RequestBody LeaveRequestDTO leaveRequestDTO){
        return ResponseEntity.ok(leaveRequestService.applyLeave(leaveRequestDTO));
    }

    @GetMapping("/balance")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER')")
    public ResponseEntity<UserDTO> getLeaveBalance(){
        return ResponseEntity.ok(leaveRequestService.getLeaveBalance());
    }

    @GetMapping("/history")
    public ResponseEntity<List<LeaveRequestDTO>> getMyLeaveHistory(){
        return ResponseEntity.ok(leaveRequestService.getMyLeaveHistory());
    }
}
