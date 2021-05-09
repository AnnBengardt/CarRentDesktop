package com.AnnaMarunko.CarRentDesktop.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Office.
 */
@Entity
@Data
@NoArgsConstructor
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officeId;

    private String city;
    private String street;
    private String email;
    private String house;

    @OneToMany(
            mappedBy = "office",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Employee> employeeList = new ArrayList<>();

    /**
     * Add employee.
     *
     * @param employee the employee
     */
    public void addEmployee(Employee employee){
        employee.setOffice(this);
        this.employeeList.add(employee);
    }

    /**
     * Remove employee.
     *
     * @param employee the employee
     */
    public void removeEmployee(Employee employee){
        this.employeeList.remove(employee);
    }

    @OneToMany(
            mappedBy = "office",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Car> carList = new ArrayList<>();

    /**
     * Add car.
     *
     * @param car the car
     */
    public void addCar(Car car){
        car.setOffice(this);
        this.carList.add(car);
    }

    /**
     * Remove car.
     *
     * @param car the car
     */
    public void removeCar(Car car){
        this.carList.remove(car);
    }

}
