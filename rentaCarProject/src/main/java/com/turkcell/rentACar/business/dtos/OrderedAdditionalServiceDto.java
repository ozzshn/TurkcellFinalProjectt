package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceDto {
	
	private int orderedAdditionalServiceTotal;
	
	private String additionalServiceName;
	private double additionalServiceDailyPrice;



}
