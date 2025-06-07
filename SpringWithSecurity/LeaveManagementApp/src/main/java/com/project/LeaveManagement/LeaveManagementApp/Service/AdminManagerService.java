package com.project.LeaveManagement.LeaveManagementApp.Service;

import com.project.LeaveManagement.LeaveManagementApp.DTO.ApprovalDTO;
import com.project.LeaveManagement.LeaveManagementApp.DTO.RegisterRequestDTO;
import com.project.LeaveManagement.LeaveManagementApp.DTO.UserDTO;
import com.project.LeaveManagement.LeaveManagementApp.Entity.LeaveRequest;
import com.project.LeaveManagement.LeaveManagementApp.Entity.LeaveStatus;
import com.project.LeaveManagement.LeaveManagementApp.Entity.User;
import com.project.LeaveManagement.LeaveManagementApp.Repository.LeaveRequestRepository;
import com.project.LeaveManagement.LeaveManagementApp.Repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class AdminManagerService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    public User assignManager(Long employeeId, Long managerId) {
        User employee = userRepository.findById(employeeId)
                .orElseThrow(()-> new RuntimeException("Employee not found!"));

        User manager = userRepository.findById(managerId)
                .orElseThrow(()-> new RuntimeException("Manager not found!"));

        if(employee.getManager() != null){
            throw new RuntimeException("Employee already has assigned to manager..");
        }

        employee.setManager(manager);

        return userRepository.save(employee);
    }

    public User changeManager(Long employeeId, Long managerId) {

        User employee = userRepository.findById(employeeId)
                .orElseThrow(()-> new RuntimeException("Employee not found!"));

        User manager = userRepository.findById(managerId)
                .orElseThrow(()-> new RuntimeException("Manager not found.."));

        if(employee.getManager().getId().equals(managerId)){
            throw new RuntimeException("Employee already assigned with same manager");
        }

        employee.setManager(manager);
        return userRepository.save(employee);

    }

    public List<UserDTO> getMyTeamMembers() {
        User currentUser = authenticationService.getCurrectUser();

        System.out.println(currentUser.getId());

//        if(currentUser.getRoles().contains("ROLE_ADMIN") || !currentUser.getRoles().contains("ROLE_MANAGER")){
//            throw new RuntimeException("User must have admin/manager role to view team members");
//        }

        return currentUser.getTeamMembers()
                .stream()
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setLeaveBalance(user.getLeaveBalance());
                    return  dto;
                })
                .collect(Collectors.toList());
    }

    public LeaveRequest approveLeave(Long id, ApprovalDTO approvalDTO) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Leave Request not found!"));

        User manager = authenticationService.getCurrectUser();
        User employee = leaveRequest.getEmployee();

        //Check if current user is the manager for this employee
        if(employee.getManager()==null || !employee.getManager().getId().equals(manager.getId())){
            throw new RuntimeException("Only his/her manager can approve the leave request..");
        }

        leaveRequest.setStatus(LeaveStatus.APPROVED);
        leaveRequest.setManagerComment(approvalDTO.getComment());

        //Deduct leave from balance
        long days = ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate());

        User user = leaveRequest.getEmployee();
        user.setLeaveBalance(user.getLeaveBalance() - days);
        leaveRequestRepository.save(leaveRequest);
        userRepository.save(user);

        return leaveRequest;
    }

    public LeaveRequest rejectLeave(Long id, ApprovalDTO approvalDTO) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Leave Request not found!"));

        User manager = authenticationService.getCurrectUser();
        User employee = leaveRequest.getEmployee();

        //Check if current user is the manager for this employee
        if(employee.getManager()==null || !employee.getManager().getId().equals(manager.getId())){
            throw new RuntimeException("Only his/her manager can approve the leave request..");
        }

        leaveRequest.setStatus(LeaveStatus.REJECTED);
        leaveRequest.setManagerComment(approvalDTO.getComment());

        return leaveRequestRepository.save(leaveRequest);
    }
}
