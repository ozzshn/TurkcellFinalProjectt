package com.turkcell.rentACar.business.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageListDto {

	private String damageInfo;

	private int modelYear;
	
	private String description;

}
