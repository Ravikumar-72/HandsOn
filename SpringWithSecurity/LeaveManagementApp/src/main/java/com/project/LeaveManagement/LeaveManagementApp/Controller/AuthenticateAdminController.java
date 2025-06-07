package com.project.LeaveManagement.LeaveManagementApp.Controller;

import com.project.LeaveManagement.LeaveManagementApp.DTO.RegisterRequestDTO;
import com.project.LeaveManagement.LeaveManagementApp.Entity.User;
import com.project.LeaveManagement.LeaveManagementApp.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AuthenticateAdminController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/")
    public ResponseEntity<User> registerAdminUser(@RequestBody RegisterRequestDTO registerRequestDTO){
        return ResponseEntity.ok(authenticationService.registerAdmin(registerRequestDTO));
    }

    @PutMapping("/{employeeId}/promote")
    public ResponseEntity<User> promoteManagerUser(@PathVariable Long employeeId){
        return ResponseEntity.ok(authenticationService.promoteManagerUser(employeeId));
    }

}
