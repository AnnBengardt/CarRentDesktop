package com.AnnaMarunko.CarRentDesktop.repos;

import com.AnnaMarunko.CarRentDesktop.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Employee repository.
 */
//@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    public Optional<Employee> findByEmail(String email);
}
