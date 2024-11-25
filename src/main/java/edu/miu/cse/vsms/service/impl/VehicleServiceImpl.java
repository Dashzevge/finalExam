package edu.miu.cse.vsms.service.impl;

import edu.miu.cse.vsms.dto.request.ServiceRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.exception.ResourceNotFoundException;
import edu.miu.cse.vsms.model.Employee;
import edu.miu.cse.vsms.model.VService;
import edu.miu.cse.vsms.repository.EmployeeRepository;
import edu.miu.cse.vsms.repository.VehicleServiceRepository;
import edu.miu.cse.vsms.service.VehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import javax.swing.text.html.Option;
import java.util.Optional;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleServiceRepository vehicleServiceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public VehicleServiceResponseDto assignService(ServiceRequestDto request) {
        // Write your code here
        Optional<Employee> employee = employeeRepository.findById(request.employeeId());
        VehicleServiceResponseDto vehicleServiceResponseDto = null;
        if (employee.isPresent()) {
        VService saved = vehicleServiceRepository.save(new VService(request.serviceName(), request.cost(), request.vehicleType(), employee.get()));

        vehicleServiceResponseDto = new VehicleServiceResponseDto(
                saved.getId(),
                saved.getServiceName(),
                saved.getCost(),
                saved.getVehicleType()
        );
        }
        return vehicleServiceResponseDto;
    }
}
