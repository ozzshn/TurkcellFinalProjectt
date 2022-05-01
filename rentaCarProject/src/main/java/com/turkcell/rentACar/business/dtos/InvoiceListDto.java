package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceListDto {
	
	
	private LocalDate invoiceCreationDate;

	private double totalPrice;

	private LocalDate startDate;

	private LocalDate returnDate;

	private String rentalCity;

	private String returnCity;
	
	private String email;
	private int userId;

	
}
