package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Car;
import com.AnnaMarunko.CarRentDesktop.repos.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepo carRepo;

    public void create(Car car){
        carRepo.save(car);
    }

    public void update(Car car) { carRepo.save(car); }

    public void delete(Car car) { carRepo.delete(car); }

    public List<Car> findAll(){
        return carRepo.findAll();
    }

    public Optional<Car> find(Long id){
        return carRepo.findById(id);
    }

    public List<Car> findByOfficeId(Long id) {return carRepo.findByOffice_OfficeId(id);}

}
