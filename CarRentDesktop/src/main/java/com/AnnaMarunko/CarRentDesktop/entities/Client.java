package com.AnnaMarunko.CarRentDesktop.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    private String lastName;
    private String firstName;

    private String cardNumber;
    private String driverLicense;
    private String passport;
    private Boolean isBlackListed;

    /*@OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Rent> rentList = new ArrayList<>();

    public void addRent(Rent rent){
        rent.setClient(this);
        this.rentList.add(rent);
    }

    public void removeRent(Rent rent){
        this.rentList.remove(rent);
    }*/

}
