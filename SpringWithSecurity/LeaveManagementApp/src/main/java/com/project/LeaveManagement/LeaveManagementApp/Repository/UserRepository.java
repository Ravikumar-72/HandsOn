package com.project.LeaveManagement.LeaveManagementApp.Repository;

import com.project.LeaveManagement.LeaveManagementApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByManagerId(Long managerId);
}
