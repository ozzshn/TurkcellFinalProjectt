package com.turkcell.rentACar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.Invoice;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer> {

	Invoice getByRental_RentalId(String rentalId);

	boolean existsByRental_RentalId(String rentalId);

	boolean existsByUser_UserId(int userId);

	List<Invoice> getByUser_UserId(int userId);

	@Query("SELECT i FROM Invoice i WHERE i.invoiceCreationDate BETWEEN :startDate AND :endDate")
	List<Invoice> getAllByBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate);
}
