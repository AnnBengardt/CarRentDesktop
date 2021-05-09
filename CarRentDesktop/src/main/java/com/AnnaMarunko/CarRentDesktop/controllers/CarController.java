package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Car;
import com.AnnaMarunko.CarRentDesktop.services.CarService;
import com.AnnaMarunko.CarRentDesktop.services.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The type Car controller.
 */
@RestController
public class CarController {

    private final CarService carService;
    private final InsuranceService insuranceService;

    /**
     * Instantiates a new Car controller.
     *
     * @param carService       the car service
     * @param insuranceService the insurance service
     */
    @Autowired
    public CarController(CarService carService, InsuranceService insuranceService){
        this.carService = carService;
        this.insuranceService = insuranceService;
    }

    /**
     * Create response entity.
     *
     * @param car the car
     * @return the response entity
     */
    @PostMapping("/api/cars")
    public ResponseEntity<?> create(@RequestBody Car car){
        carService.create(car);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping("/api/cars")
    public ResponseEntity<List<Car>> findAll(){
        final List<Car> carList = carService.findAll();
        return carList != null && !carList.isEmpty()
                ? new ResponseEntity<>(carList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Find response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/api/cars/{id}")
    public ResponseEntity<Optional<Car>> find(@PathVariable(name = "id") Long id){
        final Optional<Car> car = carService.find(id);
        return car != null
                ? new ResponseEntity<>(car, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Find by office id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/api/cars/findbyoffice/{id}")
    public ResponseEntity<List<Car>> findByOfficeId(@PathVariable(name = "id") Long id){
        final List<Car> car = carService.findByOfficeId(id);
        return car != null
                ? new ResponseEntity<List<Car>>(car, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Update car response entity.
     *
     * @param id        the id
     * @param carUpdate the car update
     * @return the response entity
     */
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

    /**
     * Delete car response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/api/cars/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable(name = "id") Long id) {
        return carService.find(id).map(car -> {
            carService.delete(car);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }
}
