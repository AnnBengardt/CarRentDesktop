package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Employee;
import com.AnnaMarunko.CarRentDesktop.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/api/employees")
    public ResponseEntity<?> create(@RequestBody Employee employee){
        employeeService.create(employee);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/employees")
    public ResponseEntity<List<Employee>> findAll(){
        final List<Employee> employeeList = employeeService.findAll();
        return employeeList != null && !employeeList.isEmpty()
                ? new ResponseEntity<>(employeeList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/employees/{id}")
    public ResponseEntity<Optional<Employee>> find(@PathVariable(name = "id") Long id){
        final Optional<Employee> employee = employeeService.find(id);
        return employee != null
                ? new ResponseEntity<>(employee, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/employees/email/{email}")
    public ResponseEntity<Optional<Employee>> findByEmail(@PathVariable(name = "email") String email){
        final Optional<Employee> employee = employeeService.findByEmail(email);
        return employee != null
                ? new ResponseEntity<>(employee, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/api/employees/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable(name = "id") Long id, @RequestBody Employee employeeUpdate) {
        return employeeService.find(id).map(employee -> {
            employee.setFirstName(employeeUpdate.getFirstName());
            employee.setLastName(employeeUpdate.getLastName());
            employee.setOffice(employeeUpdate.getOffice());
            employee.setEmail(employeeUpdate.getEmail());
            employee.setHashedPassword(employeeUpdate.getHashedPassword());
            employee.setJob(employeeUpdate.getJob());
            employeeService.update(employee);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/api/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(name = "id") Long id) {
        return employeeService.find(id).map(employee -> {
            employeeService.delete(employee);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }
}
