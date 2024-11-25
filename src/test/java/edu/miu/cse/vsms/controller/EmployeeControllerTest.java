package edu.miu.cse.vsms.controller;

import com.google.gson.Gson;
import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void createEmployee() throws Exception {
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto("john","john@gmail","1923123", LocalDate.of(1990,10,23));

        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto( 1, "john", "john@gmail","1923123", LocalDate.of(1990,10,23),null);

        Mockito.when(employeeService.addEmployee(employeeRequestDto)).thenReturn(employeeResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(employeeRequestDto))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        MockMvcResultMatchers.content().json(new Gson().toJson(employeeResponseDto))
                );
    }
}
