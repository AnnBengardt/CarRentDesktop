package com.AnnaMarunko.CarRentDesktop.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String lastName;
    private String firstName;
    private String email;
    private String hashedPassword;
    private Boolean isMainManager;

    @ManyToOne(fetch = FetchType.LAZY)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    private Office office;


}


