package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderedAdditionalServiceRequest {
    @NotNull
    private int orderedAdditionalServiceId;
    

	@NotNull
	@Min(1)
	private int orderedAdditionalServiceTotal;
	
    @NotNull
    private String rentalId;

    @NotNull
    private int additionalServiceId;
    
}