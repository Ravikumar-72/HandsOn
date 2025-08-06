package com.project.MovieBookingApp.DTO;

import lombok.Builder;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String username;
    private String email;
    private String password;

}
