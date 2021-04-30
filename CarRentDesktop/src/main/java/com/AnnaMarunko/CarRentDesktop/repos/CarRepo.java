package com.AnnaMarunko.CarRentDesktop.repos;

import com.AnnaMarunko.CarRentDesktop.entities.Car;
import com.AnnaMarunko.CarRentDesktop.entities.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
    public List<Car> findByOffice_OfficeId(Long officeId);
}
