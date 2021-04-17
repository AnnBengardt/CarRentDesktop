package com.AnnaMarunko.CarRentDesktop.repos;

import com.AnnaMarunko.CarRentDesktop.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepo extends JpaRepository<Office, Long> {
}
