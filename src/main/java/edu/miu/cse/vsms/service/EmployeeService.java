package edu.miu.cse.vsms.service;

import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    EmployeeResponseDto addEmployee(EmployeeRequestDto request);
    List<EmployeeResponseDto> getAllEmployees();
    EmployeeResponseDto getEmployeeById(Long id) throws ResourceNotFoundException;
    EmployeeResponseDto partiallyUpdateEmployee(Long id, Map<String, Object> updates);
}
