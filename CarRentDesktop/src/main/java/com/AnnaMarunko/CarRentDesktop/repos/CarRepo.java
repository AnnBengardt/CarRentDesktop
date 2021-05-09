package com.AnnaMarunko.CarRentDesktop.repos;

import com.AnnaMarunko.CarRentDesktop.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Car repository.
 */
@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
    /**
     * Find by office id list.
     *
     * @param officeId the office id
     * @return the list
     */
    public List<Car> findByOffice_OfficeId(Long officeId);
}
