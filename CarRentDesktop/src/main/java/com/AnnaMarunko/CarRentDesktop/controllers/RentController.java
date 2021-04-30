package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Rent;
import com.AnnaMarunko.CarRentDesktop.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RentController {

    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService){
        this.rentService = rentService;
    }

    @PostMapping("/api/rents")
    public ResponseEntity<?> create(@RequestBody Rent rent){
        rentService.create(rent);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/rents")
    public ResponseEntity<List<Rent>> findAll(){
        final List<Rent> rentList = rentService.findAll();
        return rentList != null && !rentList.isEmpty()
                ? new ResponseEntity<>(rentList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/rents/{id}")
    public ResponseEntity<Optional<Rent>> find(@PathVariable(name = "id") Long id){
        final Optional<Rent> rent = rentService.find(id);
        return rent != null
                ? new ResponseEntity<>(rent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/rents/findbyclient/{id}")
    public ResponseEntity<Optional<Rent>> findByClientId(@PathVariable(name = "id") Long id){
        final Optional<Rent> rent = rentService.findByClientId(id);
        return rent != null
                ? new ResponseEntity<>(rent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/rents/findbyoffice/{id}")
    public ResponseEntity<List<Rent>> findByOfficeId(@PathVariable(name = "id") Long id){
        final List<Rent> rent = rentService.findByOfficeId(id);
        return rent != null
                ? new ResponseEntity<>(rent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/api/rents/{id}")
    public ResponseEntity<?> updateRent(@PathVariable(name = "id") Long id, @RequestBody Rent rentUpdate) {
        return rentService.find(id).map(rent -> {
            rent.setClient(rentUpdate.getClient());
            rent.setCar(rentUpdate.getCar());
            rent.setRate(rentUpdate.getRate());
            rent.setStartDate(rentUpdate.getStartDate());
            rent.setEndDate(rentUpdate.getEndDate());
            rent.setFinalPrice(rentUpdate.getFinalPrice());
            rentService.update(rent);
            return new ResponseEntity<>(rent, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/api/rents/{id}")
    public ResponseEntity<?> deleteRent(@PathVariable(name = "id") Long id) {
        return rentService.find(id).map(rent -> {
            rentService.delete(rent);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }

}
