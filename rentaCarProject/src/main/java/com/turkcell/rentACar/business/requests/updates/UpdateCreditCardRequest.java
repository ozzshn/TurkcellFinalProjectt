package com.turkcell.rentACar.business.requests.updates;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {
	
	private String cardNo;
	
	private String cardHolder;

	private LocalDate expiryDate;

	private String cvvNo;

	private int userId;
}
