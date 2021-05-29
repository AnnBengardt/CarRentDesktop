package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Employee;
import com.AnnaMarunko.CarRentDesktop.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    Employee employee = new Employee();
    Employee employee1 = new Employee();

    @BeforeEach
    void setUp() {

        employee.setEmployeeId(Long.valueOf(1));
        employee.setFirstName("Ivan");
        employee.setLastName("Ivanov");
        employee.setEmail("II@mail.ru");
        employee.setHashedPassword("abcdefg");

        employee1.setEmployeeId(Long.valueOf(2));
        employee1.setFirstName("Alexandr");
        employee1.setLastName("Pushkin");
        employee1.setEmail("AP@mail.ru");
        employee1.setHashedPassword("12345");

    }

    @Test
    @DisplayName("Create an employee")
    void create() throws Exception {
        doReturn(employee).when(employeeService).create(any());

        // the POST request
        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee)))

                // the response code
                .andExpect(status().is(201));

    }

    @Test
    @DisplayName("Find all employees")
    void findAll() throws Exception {
        doReturn(Lists.newArrayList(employee, employee1)).when(employeeService).findAll();

        // the GET request
        mockMvc.perform(get("/api/employees"))
                // the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // the returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].employeeId", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Ivan")))
                .andExpect(jsonPath("$[0].lastName", is("Ivanov")))
                .andExpect(jsonPath("$[0].email", is("II@mail.ru")))
                .andExpect(jsonPath("$[0].hashedPassword", is("abcdefg")))
                .andExpect(jsonPath("$[1].employeeId", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Alexandr")))
                .andExpect(jsonPath("$[1].lastName", is("Pushkin")))
                .andExpect(jsonPath("$[1].email", is("AP@mail.ru")))
                .andExpect(jsonPath("$[1].hashedPassword", is("12345")));
    }

    @Test
    @DisplayName("Find an employee by ID")
    void find() throws Exception {
        doReturn(Optional.of(employee)).when(employeeService).find(Long.valueOf(1));

        // the GET request
        mockMvc.perform(get("/api/employees/1"))
                // the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // the returned fields
                .andExpect(jsonPath("$.employeeId", is(1)))
                .andExpect(jsonPath("$.firstName", is("Ivan")))
                .andExpect(jsonPath("$.lastName", is("Ivanov")))
                .andExpect(jsonPath("$.email", is("II@mail.ru")))
                .andExpect(jsonPath("$.hashedPassword", is("abcdefg")));
    }

    @Test
    @DisplayName("Update an employee")
    void updateEmployee() throws Exception {
        Employee employee2 = employee;
        employee2.setFirstName("Andrew");
        doReturn(Optional.of(employee)).when(employeeService).find(Long.valueOf(1));
        doReturn(employee2).when(employeeService).update(employee);

        // Execute the POST request
        mockMvc.perform(put("/api/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(asJsonString(employee2)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.employeeId", is(1)))
                .andExpect(jsonPath("$.firstName", is("Andrew")))
                .andExpect(jsonPath("$.lastName", is("Ivanov")))
                .andExpect(jsonPath("$.email", is("II@mail.ru")))
                .andExpect(jsonPath("$.hashedPassword", is("abcdefg")));
    }

    @Test
    @DisplayName("Delete an employee")
    void deleteEmployee() throws Exception {
        doReturn(Optional.of(employee)).when(employeeService).find(Long.valueOf(1));
        doReturn(true).when(employeeService).delete(employee);

        // the DELETE request
        mockMvc.perform(delete("/api/employees/{id}", 1))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Find an employee by ID")
    void findByEmail() throws Exception {
        doReturn(Optional.of(employee1)).when(employeeService).findByEmail("AP@mail.ru");

        // the GET request
        mockMvc.perform(get("/api/employees/email/AP@mail.ru"))
                // the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.employeeId", is(2)))
                .andExpect(jsonPath("$.firstName", is("Alexandr")))
                .andExpect(jsonPath("$.lastName", is("Pushkin")))
                .andExpect(jsonPath("$.email", is("AP@mail.ru")))
                .andExpect(jsonPath("$.hashedPassword", is("12345")));
    }



    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}