package edu.miu.cse.vsms.controller;

import com.google.gson.Gson;
import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
    @Mock
    private EmployeeService service;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void createEmployee() throws Exception {
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto("john","john@gmail","1923123", LocalDate.of(1990,10,23));
        long employeeId = 1L;
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto( employeeId, "john", "john@gmail","1923123", LocalDate.of(1990,10,23),null);

        Mockito.when(service.addEmployee(employeeRequestDto)).thenReturn(employeeResponseDto);

        ResponseEntity<EmployeeResponseDto> responseEntity = employeeController.addEmployee(employeeRequestDto);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(employeeResponseDto, responseEntity.getBody());
    }
}
