package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Insurance;
import com.AnnaMarunko.CarRentDesktop.services.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The type Insurance controller.
 */
@RestController
public class InsuranceController {

    private final InsuranceService insuranceService;

    /**
     * Instantiates a new Insurance controller.
     *
     * @param insuranceService the insurance service
     */
    @Autowired
    public InsuranceController(InsuranceService insuranceService){
        this.insuranceService = insuranceService;
    }

    /**
     * Create response entity.
     *
     * @param insurance the insurance
     * @return the response entity
     */
    @PostMapping("/api/insurances")
    public ResponseEntity<?> create(@RequestBody Insurance insurance){
        insuranceService.create(insurance);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping("/api/insurances")
    public ResponseEntity<List<Insurance>> findAll(){
        final List<Insurance> insuranceList = insuranceService.findAll();
        return insuranceList != null && !insuranceList.isEmpty()
                ? new ResponseEntity<>(insuranceList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Find response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/api/insurances/{id}")
    public ResponseEntity<Optional<Insurance>> find(@PathVariable(name = "id") Long id){
        final Optional<Insurance> insurance = insuranceService.find(id);
        return insurance != null
                ? new ResponseEntity<>(insurance, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Find by car id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/api/insurances/findbycar/{id}")
    public ResponseEntity<Optional<Insurance>> findByCarId(@PathVariable(name = "id") Long id){
        final Optional<Insurance> insurance = insuranceService.findByCarId(id);
        return insurance != null
                ? new ResponseEntity<>(insurance, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Update insurance response entity.
     *
     * @param id              the id
     * @param insuranceUpdate the insurance update
     * @return the response entity
     */
    @PutMapping("/api/insurances/{id}")
    public ResponseEntity<?> updateInsurance(@PathVariable(name = "id") Long id, @RequestBody Insurance insuranceUpdate) {
        return insuranceService.find(id).map(insurance -> {
            insurance.setPrice(insuranceUpdate.getPrice());
            insurance.setCar(insuranceUpdate.getCar());
            insurance.setStartDate(insuranceUpdate.getStartDate());
            insurance.setEndDate(insuranceUpdate.getEndDate());
            insuranceService.update(insurance);
            return new ResponseEntity<>(insurance, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    /**
     * Delete insurance response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/api/insurances/{id}")
    public ResponseEntity<?> deleteInsurance(@PathVariable(name = "id") Long id) {
        return insuranceService.find(id).map(insurance -> {
            insuranceService.delete(insurance);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }
}
