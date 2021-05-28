package com.AnnaMarunko.CarRentDesktop.repos;

import com.AnnaMarunko.CarRentDesktop.entities.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Rent repository.
 */
@Repository
public interface RentRepo extends JpaRepository<Rent, Long> {
    /**
     * Find by client id optional.
     *
     * @param clientId the client id
     * @return the optional
     */
    public List<Rent> findByClient_ClientId(Long clientId);

    /**
     * Find by car office id list.
     *
     * @param officeId the office id
     * @return the list
     */
    public List<Rent> findByCar_Office_OfficeId(Long officeId);

    /**
     * Find by car id list.
     *
     * @param carId the car id
     * @return the list
     */
    public List<Rent> findByCar_CarId(Long carId);
}
