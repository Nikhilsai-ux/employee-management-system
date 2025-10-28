package com.example.attendance.controller;

import com.example.attendance.dto.ApiResponse;
import com.example.attendance.dto.CreateStudentRequest;
import com.example.attendance.dto.StudentDTO;
import com.example.attendance.dto.UpdateStudentRequest;
import com.example.attendance.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class StudentController {
    
    private final StudentService studentService;
    
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<StudentDTO>> createStudent(@Valid @RequestBody CreateStudentRequest request) {
        StudentDTO student = studentService.createStudent(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Employee created successfully", student));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getAllStudents(
        @RequestParam(required = false) Boolean activeOnly) {
        List<StudentDTO> students = activeOnly != null && activeOnly 
            ? studentService.getActiveStudents() 
            : studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success(students));
    }
    
    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<StudentDTO>>> getStudentsPaginated(Pageable pageable) {
        Page<StudentDTO> students = studentService.getActiveStudents(pageable);
        return ResponseEntity.ok(ApiResponse.success(students));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentById(@PathVariable String id) {
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(ApiResponse.success(student));
    }
    
@GetMapping("/id/{employeeId}")
public ResponseEntity<ApiResponse<StudentDTO>> getStudentByEmployeeId(@PathVariable String employeeId) {
        StudentDTO student = studentService.getStudentByEmployeeId(employeeId);
        return ResponseEntity.ok(ApiResponse.success(student));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> searchStudents(@RequestParam String name) {
        List<StudentDTO> students = studentService.searchStudentsByName(name);
        return ResponseEntity.ok(ApiResponse.success(students));
    }
    
    @GetMapping("/department/{department}")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getStudentsByDepartment(@PathVariable String department) {
        List<StudentDTO> students = studentService.getStudentsByDepartment(department);
        return ResponseEntity.ok(ApiResponse.success(students));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(
        @PathVariable String id,
        @Valid @RequestBody UpdateStudentRequest request) {
        StudentDTO student = studentService.updateStudent(id, request);
        return ResponseEntity.ok(ApiResponse.success("Employee updated successfully", student));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success("Employee deleted successfully", null));
    }
    
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateStudent(@PathVariable String id) {
        studentService.deactivateStudent(id);
        return ResponseEntity.ok(ApiResponse.success("Employee deactivated successfully", null));
    }
    
    @GetMapping("/stats/count")
    public ResponseEntity<ApiResponse<Long>> getActiveStudentCount() {
        long count = studentService.getActiveStudentCount();
        return ResponseEntity.ok(ApiResponse.success(count));
    }
}
