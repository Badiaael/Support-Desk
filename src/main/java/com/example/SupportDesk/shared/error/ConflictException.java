package com.example.SupportDesk.shared.error;

public class ConflictException extends RuntimeException{
    public ConflictException(String message){
        super (message);
    }
}
