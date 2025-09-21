package com.project.springcloud.customer.controller;

import com.project.springcloud.customer.service.CustomerService;
import com.project.springcloud.customer.utils.CustomerDTO;
import com.project.springcloud.customer.utils.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> registerUser(@RequestBody CustomerDTO customer){
        customerService.registerNewCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("201","Customer Registered successfully!"));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long customerId){
        CustomerDTO customerDTO = customerService.getCustomerDetails(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customer, @PathVariable Long customerId){
        CustomerDTO customerDTO = customerService.updateCustomerDetails(customer,customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<SuccessResponse> deleteCustomer(@PathVariable Long customerId){
        customerService.deleteCustomerDetails(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("200","Customer details deleted!"));
    }

}
