package com.AnnaMarunko.CarRentDesktop.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insuranceId;

    private Date startDate;
    private Date endDate;
    private Double price;

    @OneToOne
    @JoinColumn
    @JsonIgnore
    private Car car;
}
