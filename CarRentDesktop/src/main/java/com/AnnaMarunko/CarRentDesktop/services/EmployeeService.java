package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Employee;
import com.AnnaMarunko.CarRentDesktop.repos.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Employee service.
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    /**
     * Create.
     *
     * @param employee the employee
     */
    public void create(Employee employee){
        employeeRepo.save(employee);
    }

    /**
     * Update.
     *
     * @param employee the employee
     */
    public void update(Employee employee) { employeeRepo.save(employee); }

    /**
     * Delete.
     *
     * @param employee the employee
     */
    public void delete(Employee employee) { employeeRepo.delete(employee); }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Employee> findAll(){
        return employeeRepo.findAll();
    }

    /**
     * Find optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Employee> find(Long id){
        return employeeRepo.findById(id);
    }

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    public Optional<Employee> findByEmail(String email) {return employeeRepo.findByEmail(email);}

}