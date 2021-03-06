package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Rate;
import com.AnnaMarunko.CarRentDesktop.services.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The type Rate controller.
 */
@RestController
public class RateController {

    private final RateService rateService;

    /**
     * Instantiates a new Rate controller.
     *
     * @param rateService the rate service
     */
    @Autowired
    public RateController(RateService rateService){
        this.rateService = rateService;
    }

    /**
     * Create response entity.
     *
     * @param rate the rate
     * @return the response entity
     */
    @PostMapping("/api/rates")
    public ResponseEntity<?> create(@RequestBody Rate rate){
        rateService.create(rate);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping("/api/rates")
    public ResponseEntity<List<Rate>> findAll(){
        final List<Rate> rateList = rateService.findAll();
        return rateList != null && !rateList.isEmpty()
                ? new ResponseEntity<>(rateList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Find response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/api/rates/{id}")
    public ResponseEntity<Optional<Rate>> find(@PathVariable(name = "id") Long id){
        final Optional<Rate> rate = rateService.find(id);
        return rate != null
                ? new ResponseEntity<>(rate, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Update rate response entity.
     *
     * @param id         the id
     * @param rateUpdate the rate update
     * @return the response entity
     */
    @PutMapping("/api/rates/{id}")
    public ResponseEntity<?> updateRate(@PathVariable(name = "id") Long id, @RequestBody Rate rateUpdate) {
        return rateService.find(id).map(rate -> {
            rate.setRateName(rateUpdate.getRateName());
            rate.setPrice(rateUpdate.getPrice());
            rateService.update(rate);
            return new ResponseEntity<>(rate, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    /**
     * Delete rate response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/api/rates/{id}")
    public ResponseEntity<?> deleteRate(@PathVariable(name = "id") Long id) {
        return rateService.find(id).map(rate -> {
            rateService.delete(rate);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }

}
