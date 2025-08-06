package com.project.LeaveManagement.LeaveManagementApp.Service;


import com.project.LeaveManagement.LeaveManagementApp.DTO.LeaveRequestDTO;
import com.project.LeaveManagement.LeaveManagementApp.DTO.UserDTO;
import com.project.LeaveManagement.LeaveManagementApp.Entity.LeaveRequest;
import com.project.LeaveManagement.LeaveManagementApp.Entity.LeaveStatus;
import com.project.LeaveManagement.LeaveManagementApp.Entity.User;
import com.project.LeaveManagement.LeaveManagementApp.Repository.LeaveRequestRepository;
import com.project.LeaveManagement.LeaveManagementApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;

    public LeaveRequest applyLeave(LeaveRequestDTO leaveRequestDTO) {

        User user = authenticationService.getCurrectUser();

//        LocalDate today = LocalDate.now();
        LocalDate start = leaveRequestDTO.getStartDate();
        LocalDate end = leaveRequestDTO.getEndDate();

        //validating dates are not null
        if(start == null || end == null){
            throw new RuntimeException("Start and end are required");
        }

        //validating start <= end
        if(start.isAfter(end)){
            throw new RuntimeException("Start date cannot be after end date");
        }

        //Preventing zero-days leave
        long days = ChronoUnit.DAYS.between(start,end);
        System.out.println("DAYS :"+days);
        if(days <= 0){
            throw new RuntimeException("Invalid leave duration");
        }

        //checking overlap leave request
        boolean overlaps = leaveRequestRepository.existsByEmployeeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                user.getId(),
                end,
                start
        );

        if(overlaps){
            throw new RuntimeException("Overlapping leave request already exists");
        }

        if(user.getLeaveBalance()<days){
            System.out.println("UserLeave :"+ user.getLeaveBalance());
            throw new RuntimeException("Insufficient leave balance");
        }

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setStartDate(start);
        leaveRequest.setEndDate(end);
        leaveRequest.setReason(leaveRequestDTO.getReason());
        leaveRequest.setStatus(LeaveStatus.PENDING);
        leaveRequest.setEmployee(user);

        return leaveRequestRepository.save(leaveRequest);
    }

    public UserDTO getLeaveBalance() {
        User user = authenticationService.getCurrectUser();
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setLeaveBalance(user.getLeaveBalance());
        userDTO.setManagerId(user.getManager().getId());

        return userDTO;

    }

    public List<LeaveRequestDTO> getMyLeaveHistory() {

        User currentUser = authenticationService.getCurrectUser();

        return currentUser.getLeaveRequests()
                .stream()
                .map(user -> {
                    LeaveRequestDTO leaveRequestDTO = new LeaveRequestDTO();
                    leaveRequestDTO.setStartDate(user.getStartDate());
                    leaveRequestDTO.setEndDate(user.getEndDate());
                    leaveRequestDTO.setStatus(user.getStatus());
                    leaveRequestDTO.setReason(user.getReason());
                    leaveRequestDTO.setManagerComment(user.getManagerComment());
                    return leaveRequestDTO;
                })
                .collect(Collectors.toList());
    }
}
