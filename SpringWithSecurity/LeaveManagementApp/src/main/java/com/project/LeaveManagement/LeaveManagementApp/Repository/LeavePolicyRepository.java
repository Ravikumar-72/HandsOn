package com.project.LeaveManagement.LeaveManagementApp.Repository;

import com.project.LeaveManagement.LeaveManagementApp.Entity.LeavePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeavePolicyRepository extends JpaRepository<LeavePolicy, Long> {
}
