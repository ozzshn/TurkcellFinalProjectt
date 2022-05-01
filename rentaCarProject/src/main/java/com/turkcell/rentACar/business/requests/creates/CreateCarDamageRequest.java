package com.turkcell.rentACar.business.requests.creates;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarDamageRequest {

	@NotNull
	@Size(min = 2)
	private String damageInfo;

	@NotNull
	@Min(1)
	private int carId;

}
