package com.AnnaMarunko.CarRentDesktop.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    private String brand;
    private Double startingPrice;
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Office office;

    @OneToOne(mappedBy = "car",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Insurance insurance;

    @OneToMany(
            mappedBy = "car",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Rent> rentList = new ArrayList<>();
    public void addRent(Rent rent){
        rent.setCar(this);
        this.rentList.add(rent);
    }
    public void removeRent(Rent rent){
        this.rentList.remove(rent);
    }

}
