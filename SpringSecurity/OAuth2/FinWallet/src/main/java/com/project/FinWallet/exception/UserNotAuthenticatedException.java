package com.project.FinWallet.exception;

public class UserNotAuthenticatedException extends RuntimeException{
    public UserNotAuthenticatedException(String msg){
        super(msg);
    }
}
