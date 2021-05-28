package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Car;
import com.AnnaMarunko.CarRentDesktop.repos.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Car service.
 */
@Service
public class CarService {

    @Autowired
    private CarRepo carRepo;

    /**
     * Create.
     *
     * @param car the car
     */
    public Car create(Car car){
        return carRepo.save(car);
    }

    /**
     * Update.
     *
     * @param car the car
     */
    public Car update(Car car) { return carRepo.save(car); }

    /**
     * Delete.
     *
     * @param car the car
     */
    public Boolean delete(Car car) { carRepo.delete(car);
    return true;}

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Car> findAll(){
        return carRepo.findAll();
    }

    /**
     * Find optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Car> find(Long id){
        return carRepo.findById(id);
    }

    /**
     * Find by office id list.
     *
     * @param id the id
     * @return the list
     */
    public List<Car> findByOfficeId(Long id) {return carRepo.findByOffice_OfficeId(id);}

}
