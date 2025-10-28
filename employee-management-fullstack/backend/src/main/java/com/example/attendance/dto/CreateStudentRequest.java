package com.example.attendance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateStudentRequest(
    @NotBlank(message = "Name is required")
    String name,
    
    @NotBlank(message = "Employee ID is required")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "Employee ID must contain only uppercase letters, numbers, and hyphens")
    @com.fasterxml.jackson.annotation.JsonAlias("rollNumber")
    String employeeId,
    
    @Email(message = "Invalid email format")
    String email,
    
    String phone,
    
    String department,
    
    @Positive(message = "Year must be positive")
    Integer year
) {}
