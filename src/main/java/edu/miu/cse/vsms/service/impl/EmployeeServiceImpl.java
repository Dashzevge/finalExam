package edu.miu.cse.vsms.service.impl;

import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.exception.ResourceNotFoundException;
import edu.miu.cse.vsms.model.Employee;
import edu.miu.cse.vsms.model.VService;
import edu.miu.cse.vsms.repository.EmployeeRepository;
import edu.miu.cse.vsms.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponseDto addEmployee(EmployeeRequestDto request) {
        // Write your code here
        Employee employee = new Employee(request.name(), request.email(), request.phone(), request.hireDate());
        Employee savedEmp =  employeeRepository.save(employee);
        List<VehicleServiceResponseDto> vehicleServiceResponseDto = new ArrayList<>();

        if (savedEmp.getVServices() != null) {
            for(VService service: savedEmp.getVServices()) {
                VehicleServiceResponseDto serviceResponseDto = new VehicleServiceResponseDto(
                        service.getId(),
                        service.getServiceName(),
                        service.getCost(),
                        service.getVehicleType()
                );
                vehicleServiceResponseDto.add(serviceResponseDto);
            }
        }

        return new EmployeeResponseDto(savedEmp.getId(),
                savedEmp.getName(),
                savedEmp.getEmail(),
                savedEmp.getPhone(),
                savedEmp.getHireDate(),
                vehicleServiceResponseDto);
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        // Write your code here
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDto> employeeResponseDto = new ArrayList<>();
        employees.forEach(employee -> employeeResponseDto.add(mapToResponseDto(employee)));
        return employeeResponseDto;
    }

    @Override
    public EmployeeResponseDto getEmployeeById(Long id) throws ResourceNotFoundException {
        // Write your code here
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new EntityNotFoundException("Employee not found with id " + id);
        }
        EmployeeResponseDto employeeResponseDto = mapToResponseDto(employee);
        return employeeResponseDto;
    }

    @Override
    public EmployeeResponseDto partiallyUpdateEmployee(Long id, Map<String, Object> updates) {
        // Fetch the employee by ID or throw an exception if not found
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + id));


        // Apply each update based on the key
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    // Write your code here
                  employee.setName((String) value);
                    break;
                case "email":
                    // Write your code here
                 employee.setEmail((String) value);
                    break;
                case "phone":
                    // Write your code here
                 employee.setPhone((String) value);
                    break;
                case "hireDate":
                    // Write your code here
                 employee.setHireDate((LocalDate) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });
        // Write your code here
        Employee saved = employeeRepository.save(employee);
        EmployeeResponseDto employeeResponseDto = mapToResponseDto(saved);
        return employeeResponseDto;
    }

    private EmployeeResponseDto mapToResponseDto(Employee employee) {
        List<VehicleServiceResponseDto> serviceDtos = employee.getVServices().stream()
                .map(service -> new VehicleServiceResponseDto(
                        service.getId(),
                        service.getServiceName(),
                        service.getCost(),
                        service.getVehicleType()
                )).toList();

        return new EmployeeResponseDto(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getHireDate(),
                serviceDtos
        );
    }
}
