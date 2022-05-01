package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {
	
    private LocalDate startDate;
    
    private LocalDate returnDate;
    
    private boolean rentStatus;
    
    private int rentalCityId;
    
    private int returnCityId;
    
    private int carId;
    
    private CarDto carDto;
    
    private String brandName;

    private String colorName;
    
    private double additionalServicePrice;
    
    private int orderedAdditionalProductAmount;
}
