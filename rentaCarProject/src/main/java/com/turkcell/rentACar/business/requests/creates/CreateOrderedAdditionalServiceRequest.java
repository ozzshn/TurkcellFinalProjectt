package com.turkcell.rentACar.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalServiceRequest {
    @NotNull
    private String rentalId;

    @NotNull
    private int additionalServiceId;
   
    @NotNull
    private int orderedAdditionalServiceTotal;
  
}