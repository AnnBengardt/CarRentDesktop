package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Rent;
import com.AnnaMarunko.CarRentDesktop.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The type Rent controller.
 */
@RestController
public class RentController {

    private final RentService rentService;

    /**
     * Instantiates a new Rent controller.
     *
     * @param rentService the rent service
     */
    @Autowired
    public RentController(RentService rentService){
        this.rentService = rentService;
    }

    /**
     * Create response entity.
     *
     * @param rent the rent
     * @return the response entity
     */
    @PostMapping("/api/rents")
    public ResponseEntity<?> create(@RequestBody Rent rent){
        rentService.create(rent);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping("/api/rents")
    public ResponseEntity<List<Rent>> findAll(){
        final List<Rent> rentList = rentService.findAll();
        return rentList != null && !rentList.isEmpty()
                ? new ResponseEntity<>(rentList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Find response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/api/rents/{id}")
    public ResponseEntity<Optional<Rent>> find(@PathVariable(name = "id") Long id){
        final Optional<Rent> rent = rentService.find(id);
        return rent != null
                ? new ResponseEntity<>(rent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Find by client id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/api/rents/findbyclient/{id}")
    public ResponseEntity<Optional<Rent>> findByClientId(@PathVariable(name = "id") Long id){
        final Optional<Rent> rent = rentService.findByClientId(id);
        return rent != null
                ? new ResponseEntity<>(rent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Find by office id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/api/rents/findbyoffice/{id}")
    public ResponseEntity<List<Rent>> findByOfficeId(@PathVariable(name = "id") Long id){
        final List<Rent> rent = rentService.findByOfficeId(id);
        return rent != null
                ? new ResponseEntity<>(rent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Find by car response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/api/rents/findbycar/{id}")
    public ResponseEntity<List<Rent>> findByCar(@PathVariable(name = "id") Long id){
        final List<Rent> rent = rentService.findByCarId(id);
        return rent != null
                ? new ResponseEntity<>(rent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Update rent response entity.
     *
     * @param id         the id
     * @param rentUpdate the rent update
     * @return the response entity
     */
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

    /**
     * Delete rent response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/api/rents/{id}")
    public ResponseEntity<?> deleteRent(@PathVariable(name = "id") Long id) {
        return rentService.find(id).map(rent -> {
            rentService.delete(rent);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }

}
