package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.Rental;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

@Repository
public interface RentalDao extends JpaRepository<Rental, String> {
	
    Rental getByRentalId(String rentalId);

	boolean existsByCar_CarId(int carId);

	List<Rental> getByCar_CarId(int id);

	List<OrderedAdditionalService> getOrderedAdditionalServicesByRentalId(String rentalId);

}
