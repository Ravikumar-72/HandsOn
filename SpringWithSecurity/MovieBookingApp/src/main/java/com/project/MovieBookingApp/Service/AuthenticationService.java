package com.project.MovieBookingApp.Service;

import com.project.MovieBookingApp.DTO.LoginRequestDTO;
import com.project.MovieBookingApp.DTO.LoginResponseDTO;
import com.project.MovieBookingApp.DTO.RegisterRequestDTO;
import com.project.MovieBookingApp.Entity.User;
import com.project.MovieBookingApp.JWTAuthentication.JwtService;
import com.project.MovieBookingApp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public User registerNormalUser(RegisterRequestDTO registerRequestDTO) {

        if(userRepo.findByUsername(registerRequestDTO.getUsername()).isPresent()){
            throw new RuntimeException("User already registered!");
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        return userRepo.save(user);
    }

    public User registerAdminUser(RegisterRequestDTO registerRequestDTO) {
        if(userRepo.findByUsername(registerRequestDTO.getUsername()).isPresent()){
            throw new RuntimeException("User already registered!");
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        return userRepo.save(user);
    }


    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        User user = userRepo.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(()-> new RuntimeException("User not found!"));

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
}
