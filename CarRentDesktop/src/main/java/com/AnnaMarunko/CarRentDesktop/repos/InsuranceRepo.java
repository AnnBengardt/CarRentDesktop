package com.AnnaMarunko.CarRentDesktop.repos;

import com.AnnaMarunko.CarRentDesktop.entities.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Insurance repository.
 */
@Repository
public interface InsuranceRepo extends JpaRepository<Insurance, Long> {
    /**
     * Find by car id optional.
     *
     * @param carId the car id
     * @return the optional
     */
    public Optional<Insurance> findByCar_CarId(Long carId);
}
