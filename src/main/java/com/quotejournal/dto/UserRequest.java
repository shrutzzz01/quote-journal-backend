package com.quotejournal.dto;

import jakarta.validation.constraints.*;

public record UserRequest(
        @NotBlank(message="Username is required")
        @Size(max=20)
        String name,
        @NotBlank(message="Email is required")
        @Size(max=50)
        @Email(message="Email should be valid")
        String email,
        @NotBlank(message="Password can not be null")
        @Size(min=6, max=20, message="Password should be between 6 to 20 characters")
        String password
)
{}
