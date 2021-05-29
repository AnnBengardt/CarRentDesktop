package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Employee;
import com.AnnaMarunko.CarRentDesktop.repos.EmployeeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @MockBean
    EmployeeRepo employeeRepo;

    Employee employee = new Employee();
    Employee employee1 = new Employee();

    @BeforeEach
    void setUp() {
        employee.setIsMainManager(null);
        employee.setJob(null);
        employee.setOffice(null);
        employee.setEmployeeId(Long.valueOf(1));
        employee.setFirstName("Ivan");
        employee.setLastName("Ivanov");
        employee.setEmail("II@mail.ru");
        employee.setHashedPassword("abcdefg");

        employee1.setIsMainManager(null);
        employee1.setJob(null);
        employee1.setOffice(null);
        employee1.setEmployeeId(Long.valueOf(2));
        employee1.setFirstName("Alexandr");
        employee1.setLastName("Pushkin");
        employee1.setEmail("AP@mail.ru");
        employee1.setHashedPassword("12345");
    }

    @Test
    @DisplayName("Create an employee")
    void create() {
        doReturn(employee1).when(employeeRepo).save(employee1);

        // Execute the service call
        Employee returnedValue = employeeService.create(employee1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved employee should not be null");
    }

    @Test
    @DisplayName("Update an employee")
    void update() {
        employee1.setEmail("newemail@yandex.ru");
        doReturn(employee1).when(employeeRepo).save(employee1);

        // Execute the service call
        Employee returnedValue = employeeService.update(employee1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved employee should not be null");
        Assertions.assertSame(employee1, returnedValue, "The employee returned was not the same as the mock");
    }

    @Test
    @DisplayName("Delete an employee")
    void delete() {
        doNothing().when(employeeRepo).delete(employee1);

        // Execute the service call
        Boolean returnedValue = employeeService.delete(employee1);

        // Assert the response
        Assertions.assertEquals(true, returnedValue, "The deleted employee should return true");


    }

    @Test
    @DisplayName("Find all employees")
    void findAll() {
        doReturn(Arrays.asList(employee, employee1)).when(employeeRepo).findAll();

        // Execute the service call
        List<Employee> employees = employeeService.findAll();

        // Assert the response
        Assertions.assertEquals(2, employees.size(), "findAll should return 2 employees!");
    }

    @Test
    @DisplayName("Find an employee by ID")
    void find() {

        doReturn(Optional.of(employee)).when(employeeRepo).findById(Long.valueOf(1));

        // Execute the service call
        Optional<Employee> returnedValue = employeeService.find(Long.valueOf(1));

        // Assert the response
        Assertions.assertTrue(returnedValue.isPresent(), "Employee was not found");
        Assertions.assertSame(employee, returnedValue.get(), "The employee returned was not the same as the mock");

    }

    @Test
    @DisplayName("Find an employee by ID (fail)")
    void findNotFound() {
        doReturn(Optional.empty()).when(employeeRepo).findById(Long.valueOf(22));

        // Execute the service call
        Optional<Employee> returnedValue = employeeService.find(Long.valueOf(22));

        // Assert the response
        Assertions.assertFalse(returnedValue.isPresent(), "Employee should not be found");
    }

    @Test
    @DisplayName("Find an employee by email")
    void findByEmail() {

        doReturn(Optional.of(employee)).when(employeeRepo).findByEmail("II@mail.ru");

        // Execute the service call
        Optional<Employee> returnedValue = employeeService.findByEmail("II@mail.ru");

        // Assert the response
        Assertions.assertTrue(returnedValue.isPresent(), "Employee was not found");
        Assertions.assertSame(employee, returnedValue.get(), "The employee returned was not the same as the mock");


    }
}