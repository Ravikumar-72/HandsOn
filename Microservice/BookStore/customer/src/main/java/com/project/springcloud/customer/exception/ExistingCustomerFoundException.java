package com.project.springcloud.customer.exception;

public class ExistingCustomerFoundException extends RuntimeException{

    public ExistingCustomerFoundException(String message){
        super(message);
    }
}
