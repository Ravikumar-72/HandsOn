package com.project.springcloud.customer.service;

import com.project.springcloud.customer.entity.Customer;
import com.project.springcloud.customer.exception.CustomerNotFoundException;
import com.project.springcloud.customer.exception.ExistingCustomerFoundException;
import com.project.springcloud.customer.repository.CustomerRepository;
import com.project.springcloud.customer.utils.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void registerNewCustomer(CustomerDTO customer) {
        boolean customerExists = customerRepository.existsByEmail(customer.getEmail());
        if(customerExists){
            throw new ExistingCustomerFoundException("Email already registered!");
        }
        Customer newCustomer = new Customer();
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setUserName(customer.getUserName());
        newCustomer.setMobileNumber(customer.getMobileNumber());
        newCustomer.setPassword(customer.getPassword());

        customerRepository.save(newCustomer);
    }

    public CustomerDTO getCustomerDetails(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                ()-> new CustomerNotFoundException("Customer not found for the ID :"+customerId)
        );

        return new CustomerDTO(customer);
    }

    public CustomerDTO updateCustomerDetails(CustomerDTO customer, Long customerId) {
        Customer updateCustomer = customerRepository.findById(customerId).orElseThrow(
                ()-> new CustomerNotFoundException("Customer not found for the ID :"+customerId)
        );
        updateCustomer.setEmail(customer.getEmail());
        updateCustomer.setUserName(customer.getUserName());
        updateCustomer.setMobileNumber(customer.getMobileNumber());
        updateCustomer.setPassword(customer.getPassword());

        customerRepository.save(updateCustomer);
        return new CustomerDTO(updateCustomer);
    }

    public void deleteCustomerDetails(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                ()-> new CustomerNotFoundException("Customer not found for the ID :"+customerId)
        );

        customerRepository.deleteById(customerId);
    }
}
