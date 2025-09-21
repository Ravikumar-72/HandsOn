package com.project.springcloud.customer.utils;

import com.project.springcloud.customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String email;
    private String userName;
    private String mobileNumber;
    private String password;

    public CustomerDTO(Customer customer){
        this.setPassword(customer.getPassword());
        this.setEmail(customer.getEmail());
        this.setMobileNumber(customer.getMobileNumber());
        this.setUserName(customer.getUserName());
    }
}
