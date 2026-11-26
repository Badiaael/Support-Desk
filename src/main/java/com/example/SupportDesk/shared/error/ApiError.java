package com.example.SupportDesk.shared.error;

import java.util.Map;

public record ApiError(String message ,String code,  Map <String , String> details) {
    public static ApiError of(String code , String message){
        return new ApiError(code, message, Map.of());
    }
    
}
