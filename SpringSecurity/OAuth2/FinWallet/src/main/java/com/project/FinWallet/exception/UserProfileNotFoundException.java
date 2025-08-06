package com.project.FinWallet.exception;

public class UserProfileNotFoundException extends RuntimeException{

    public UserProfileNotFoundException(String resource, String fieldName, String fieldValue){
        super(String.format("%s not found for the field %s : %s",resource,fieldName,fieldValue));
    }
}
