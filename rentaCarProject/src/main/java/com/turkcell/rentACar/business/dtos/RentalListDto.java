package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalListDto {
		
    private LocalDate startDate;
    
    private LocalDate returnDate;
    
    private boolean rentStatus;
    
    private String brandName;
    
    private String colorName;
}
