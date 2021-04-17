package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Employee;
import com.AnnaMarunko.CarRentDesktop.repos.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public void create(Employee employee){
        employeeRepo.save(employee);
    }

    public void update(Employee employee) { employeeRepo.save(employee); }

    public void delete(Employee employee) { employeeRepo.delete(employee); }

    public List<Employee> findAll(){
        return employeeRepo.findAll();
    }

    public Optional<Employee> find(Long id){
        return employeeRepo.findById(id);
    }

    public Optional<Employee> findByEmail(String email) {return employeeRepo.findByEmail(email);}

}