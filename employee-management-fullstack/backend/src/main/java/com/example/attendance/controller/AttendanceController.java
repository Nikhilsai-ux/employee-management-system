package com.example.attendance.controller;

import com.example.attendance.dto.*;
import com.example.attendance.model.Attendance;
import com.example.attendance.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    
    private final AttendanceService attendanceService;
    
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }
    
    @PostMapping("/mark")
    public ResponseEntity<ApiResponse<AttendanceDTO>> markAttendance(
        @Valid @RequestBody MarkAttendanceRequest request) {
        AttendanceDTO attendance = attendanceService.markAttendance(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Attendance marked successfully", attendance));
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<ApiResponse<List<AttendanceDTO>>> getAttendanceForDate(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AttendanceDTO> attendance = attendanceService.getAttendanceForDate(date);
        return ResponseEntity.ok(ApiResponse.success(attendance));
    }
    
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<AttendanceDTO>>> getTodayAttendance() {
        List<AttendanceDTO> attendance = attendanceService.getAttendanceForDate(LocalDate.now());
        return ResponseEntity.ok(ApiResponse.success(attendance));
    }
    
    @GetMapping("/date/{date}/paginated")
    public ResponseEntity<ApiResponse<Page<AttendanceDTO>>> getAttendanceForDatePaginated(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        Pageable pageable) {
        Page<AttendanceDTO> attendance = attendanceService.getAttendanceForDate(date, pageable);
        return ResponseEntity.ok(ApiResponse.success(attendance));
    }
    
    @GetMapping("/range")
    public ResponseEntity<ApiResponse<List<AttendanceDTO>>> getAttendanceByDateRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AttendanceDTO> attendance = attendanceService.getAttendanceByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(attendance));
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<StudentAttendanceHistoryDTO>> getStudentAttendanceHistory(
        @PathVariable String employeeId) {
        StudentAttendanceHistoryDTO history = attendanceService.getStudentAttendanceHistory(employeeId);
        return ResponseEntity.ok(ApiResponse.success(history));
    }
    
    @GetMapping("/employee/{employeeId}/range")
    public ResponseEntity<ApiResponse<StudentAttendanceHistoryDTO>> getStudentAttendanceHistoryByRange(
        @PathVariable String employeeId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        StudentAttendanceHistoryDTO history = attendanceService.getStudentAttendanceHistoryByDateRange(
            employeeId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(history));
    }
    
    @GetMapping("/employee/{employeeId}/stats")
    public ResponseEntity<ApiResponse<AttendanceStatsDTO>> getStudentStatistics(
        @PathVariable String employeeId) {
        AttendanceStatsDTO stats = attendanceService.getStudentStatistics(employeeId);
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
    
    @GetMapping("/employee/{employeeId}/stats/range")
    public ResponseEntity<ApiResponse<AttendanceStatsDTO>> getStudentStatisticsByRange(
        @PathVariable String employeeId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        AttendanceStatsDTO stats = attendanceService.getStudentStatisticsByDateRange(
            employeeId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<AttendanceDTO>>> getAttendanceByStatus(
        @PathVariable Attendance.Status status,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AttendanceDTO> attendance = attendanceService.getAttendanceByStatus(startDate, endDate, status);
        return ResponseEntity.ok(ApiResponse.success(attendance));
    }
}
