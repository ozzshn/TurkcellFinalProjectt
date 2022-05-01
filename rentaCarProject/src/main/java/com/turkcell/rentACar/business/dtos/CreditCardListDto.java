package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardListDto {

	private String creditCardNo;
	
	private LocalDate expiryDate;

	private String cvvNo;

	private String cardHolder;

}
