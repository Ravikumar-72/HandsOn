package com.project.LeaveManagement.LeaveManagementApp.Service;

import com.project.LeaveManagement.LeaveManagementApp.DTO.LoginRequestDTO;
import com.project.LeaveManagement.LeaveManagementApp.DTO.LoginResponseDTO;
import com.project.LeaveManagement.LeaveManagementApp.DTO.RegisterRequestDTO;
import com.project.LeaveManagement.LeaveManagementApp.Entity.User;
import com.project.LeaveManagement.LeaveManagementApp.JwtAuthentication.JwtService;
import com.project.LeaveManagement.LeaveManagementApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthenticationService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public User registerAdmin(RegisterRequestDTO registerRequestDTO) {
        if(userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()){
            throw new RuntimeException("Admin user already registered! Please login..");
        }

        HashSet<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_MANAGER");
        roles.add("ROLE_EMPLOYEE");

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User promoteManagerUser(Long employeeId) {
        if(!userRepository.findById(employeeId).isPresent()){
            throw new RuntimeException("Employee is not found to promote as manager..");
        }

        User currentUser = getCurrectUser();
        User employee = userRepository.findById(employeeId)
                .orElseThrow(()-> new RuntimeException("Employee not found!"));

        HashSet<String> roles = new HashSet<>();
        roles.add("ROLE_MANAGER");
        roles.add("ROLE_EMPLOYEE");

        employee.setManager(currentUser);
        employee.setRoles(roles);

        return userRepository.save(employee);
    }

    public User registerUser(RegisterRequestDTO registerRequestDTO) {
        if(userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()){
            throw new RuntimeException("Manager is already registered! Please login..");
        }

        HashSet<String> roles = new HashSet<>();
        roles.add("ROLE_EMPLOYEE");

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found! Please register.."));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );

        String token = jwtService.generateToken(user);

        return LoginResponseDTO.builder()
                .jwtToken(token)
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }

    public User getCurrectUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found!"));
    }
}
