package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalServiceRequest {
    @NotNull
    private int additionalServiceId;

    @NotNull
    @Size(min = 2, max = 100)
    private String additionalServiceName;

    @NotNull
    @Min(0)
    private double additionalServiceDailyPrice;
    
}
