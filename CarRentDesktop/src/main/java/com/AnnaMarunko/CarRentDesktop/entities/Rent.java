package com.AnnaMarunko.CarRentDesktop.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

/**
 * The type Rent.
 */
@Entity
@Data
@NoArgsConstructor
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentId;

    private Date startDate;
    private Date endDate;

    private Double finalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rate rate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;

}
