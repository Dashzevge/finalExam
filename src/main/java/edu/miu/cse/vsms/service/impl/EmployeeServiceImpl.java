package edu.miu.cse.vsms.service.impl;

import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.model.Employee;
import edu.miu.cse.vsms.model.VService;
import edu.miu.cse.vsms.repository.EmployeeRepository;
import edu.miu.cse.vsms.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        return null;
    }

    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {
        // Write your code here

        return null;
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

                    break;
                case "email":
                    // Write your code here

                    break;
                case "phone":
                    // Write your code here

                    break;
                case "hireDate":
                    // Write your code here

                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });
        // Write your code here

        return null;
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
