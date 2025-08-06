package com.project.FinWallet.exception;


public class ExpenseNotFoundException extends RuntimeException{

    public ExpenseNotFoundException(String resource, String fieldName, String fieldValue){
        super(String.format("%s not found for the field %s : %s",resource,fieldName,fieldValue));
    }
}
