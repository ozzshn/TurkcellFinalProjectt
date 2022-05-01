package com.turkcell.rentACar.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {
    @NotNull
    @NotBlank
    @Size(min=2,max=50)
    private String maintenanceDescription;

    @NotNull
    @NotBlank
    @Positive
    private int carId;
    
    @NotNull
    private LocalDate returnDate;


}
