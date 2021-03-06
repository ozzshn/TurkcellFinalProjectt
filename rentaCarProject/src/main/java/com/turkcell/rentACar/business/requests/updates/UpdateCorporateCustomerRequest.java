package com.turkcell.rentACar.business.requests.updates;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	
	@NotNull
	@Min(1)
	private int userId;
	
	@NotNull
	@Size(min = 2, max = 30)
	private String companyName;

	@NotNull
	@Size(min = 2, max = 30)
	private String taxNumber;

	@NotNull
	@Size(min = 2, max = 20)
	@Email(message = "This value is not email!")
	private String email;

	@NotNull
	@Size(min = 2, max = 30)
	private String password;

}
