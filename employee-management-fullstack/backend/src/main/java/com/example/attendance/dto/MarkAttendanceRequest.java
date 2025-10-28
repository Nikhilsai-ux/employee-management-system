package com.example.attendance.dto;

import com.example.attendance.model.Attendance;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MarkAttendanceRequest(
    @NotBlank(message = "Employee ID is required")
    @com.fasterxml.jackson.annotation.JsonAlias("rollNumber")
    String employeeId,
    
    @NotNull(message = "Status is required")
    Attendance.Status status,
    
    LocalDate date
) {}
