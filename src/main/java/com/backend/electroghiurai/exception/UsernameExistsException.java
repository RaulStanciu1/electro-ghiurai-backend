package com.backend.electroghiurai.exception;

public class UsernameExistsException extends RuntimeException{
    public UsernameExistsException(String message){
        super(message);
    }
}
