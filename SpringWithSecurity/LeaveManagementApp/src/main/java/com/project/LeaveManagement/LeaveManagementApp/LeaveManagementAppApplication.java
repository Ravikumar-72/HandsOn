package com.project.LeaveManagement.LeaveManagementApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LeaveManagementAppApplication {

	public static void main(String[] args) {

        SpringApplication.run(LeaveManagementAppApplication.class, args);
	}
}
