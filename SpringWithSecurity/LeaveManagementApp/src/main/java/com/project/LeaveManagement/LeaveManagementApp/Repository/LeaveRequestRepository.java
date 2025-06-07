package com.project.LeaveManagement.LeaveManagementApp.Repository;

import com.project.LeaveManagement.LeaveManagementApp.Entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    boolean existsByEmployeeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long employeeId, LocalDate endDate, LocalDate startDate);

    Optional<Object> findByEmployeeId(Long id);
}
