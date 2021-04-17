package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Office;
import com.AnnaMarunko.CarRentDesktop.services.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OfficeController {

    private final OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService){
        this.officeService = officeService;
    }

    @PostMapping("/api/offices")
    public ResponseEntity<?> create(@RequestBody Office office){
        officeService.create(office);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/offices")
    public ResponseEntity<List<Office>> findAll(){
        final List<Office> officeList = officeService.findAll();
        return officeList != null && !officeList.isEmpty()
                ? new ResponseEntity<>(officeList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/offices/{id}")
    public ResponseEntity<Optional<Office>> find(@PathVariable(name = "id") Long id){
        final Optional<Office> office = officeService.find(id);
        return office != null
                ? new ResponseEntity<>(office, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/api/offices/{id}")
    public ResponseEntity<?> updateOffice(@PathVariable(name = "id") Long id, @RequestBody Office officeUpdate) {
        return officeService.find(id).map(office -> {
            office.setCity(officeUpdate.getCity());
            office.setStreet(officeUpdate.getStreet());
            office.setEmail(officeUpdate.getEmail());
            office.setCarList(officeUpdate.getCarList());
            office.setEmployeeList(office.getEmployeeList());
            officeService.update(office);
            return new ResponseEntity<>(office, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/api/offices/{id}")
    public ResponseEntity<?> deleteOffice(@PathVariable(name = "id") Long id) {
        return officeService.find(id).map(office -> {
            officeService.delete(office);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }

}
