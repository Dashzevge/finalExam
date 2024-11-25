package edu.miu.cse.vsms.controller;

import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.exception.ResourceNotFoundException;
import edu.miu.cse.vsms.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // Add a new employee
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> addEmployee(@RequestBody EmployeeRequestDto request) {
        // Write your code here
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.addEmployee(request));
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        // Write your code here
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllEmployees());
    }

    // Get a specific employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) throws ResourceNotFoundException {
        // Write your code here
        Optional<EmployeeResponseDto> employee =  Optional.of(employeeService.getEmployeeById(id));
        if (employee.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(employee.get());
        }
        else {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            throw new ResourceNotFoundException("Employee not found:" + id);
        }
    }

    // Update partially an existing employee
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> partiallyUpdateEmployee(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.partiallyUpdateEmployee(id, updates));
    }
}
