package com.example.SupportDesk.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientRequest (
    @NotBlank(message ="Name is required")
    @Size(max = 150 , message = "Name must not exceed 150 characters !")
    String name ,

    @NotBlank(message = "Email is required")
    @Email(message = "the main is invalid")
    @Size(max = 255 , message = "Email must not exceed 255 characters!")
    String contactEmail
){}
