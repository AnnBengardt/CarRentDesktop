package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Car;
import com.AnnaMarunko.CarRentDesktop.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService){
        this.carService = carService;
    }

    @PostMapping("/api/cars")
    public ResponseEntity<?> create(@RequestBody Car car){
        carService.create(car);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/cars")
    public ResponseEntity<List<Car>> findAll(){
        final List<Car> carList = carService.findAll();
        return carList != null && !carList.isEmpty()
                ? new ResponseEntity<>(carList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/cars/{id}")
    public ResponseEntity<Optional<Car>> find(@PathVariable(name = "id") Long id){
        final Optional<Car> car = carService.find(id);
        return car != null
                ? new ResponseEntity<>(car, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/api/cars/{id}")
    public ResponseEntity<?> updateCar(@PathVariable(name = "id") Long id, @RequestBody Car carUpdate) {
        return carService.find(id).map(car -> {
            car.setBrand(carUpdate.getBrand());
            car.setOffice(carUpdate.getOffice());
            car.setInsurance(carUpdate.getInsurance());
            car.setStartingPrice(carUpdate.getStartingPrice());
            car.setStatus(carUpdate.getStatus());
            carService.update(car);
            return new ResponseEntity<>(car, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/api/cars/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable(name = "id") Long id) {
        return carService.find(id).map(car -> {
            carService.delete(car);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }
}
