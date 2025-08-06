package com.project.FinWallet.exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String resource, String fieldName, String fieldValue){
        super(String.format("%s not found for the field %s : %s",resource,fieldName,fieldValue));
    }
}
