package com.project.LeaveManagement.LeaveManagementApp.Controller;

import com.project.LeaveManagement.LeaveManagementApp.DTO.ApprovalDTO;
import com.project.LeaveManagement.LeaveManagementApp.DTO.RegisterRequestDTO;
import com.project.LeaveManagement.LeaveManagementApp.DTO.UserDTO;
import com.project.LeaveManagement.LeaveManagementApp.Entity.LeaveRequest;
import com.project.LeaveManagement.LeaveManagementApp.Entity.User;
import com.project.LeaveManagement.LeaveManagementApp.Service.AdminManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminManagerController {

    @Autowired
    private AdminManagerService adminManagerService;

    @PutMapping("/{employeeId}/assign/{managerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> assignManager(@PathVariable Long employeeId, @PathVariable Long managerId){
        return ResponseEntity.ok(adminManagerService.assignManager(employeeId, managerId));
    }

    @PutMapping("/{employeeId}/change/{managerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> changeManager(@PathVariable Long employeeId, @PathVariable Long managerId){
        return ResponseEntity.ok(adminManagerService.changeManager(employeeId, managerId));
    }

    @GetMapping("/myteam")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<UserDTO>> getMyTeamMembers(){
        return ResponseEntity.ok(adminManagerService.getMyTeamMembers());
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<LeaveRequest> approveLeave(@PathVariable Long id, @RequestBody ApprovalDTO approvalDTO){
        return ResponseEntity.ok(adminManagerService.approveLeave(id, approvalDTO));
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<LeaveRequest> rejectLeave(@PathVariable Long id, @RequestBody ApprovalDTO approvalDTO){
        return ResponseEntity.ok(adminManagerService.rejectLeave(id, approvalDTO));
    }

}
