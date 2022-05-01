package com.turkcell.rentACar.business.requests.deletes;

import javax.validation.constraints.Min;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteIndividualCustomerRequest {

	@NotNull
	@Min(1)
	private int individualCustomerId;

}
