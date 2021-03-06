package com.turkcell.rentACar.business.requests.creates;

import java.time.LocalDate;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateCreditCardRequest {

	@NotNull
	@Size(min = 2)
	private String creditCardNo;

	@NotNull
	@Size(min = 2)
	private String cardHolder;

	@NotNull
	private LocalDate expiryDate;

	@NotNull
	@Size(min = 3)
	private String cvvNo;

	@NotNull
	private int userId;
}
