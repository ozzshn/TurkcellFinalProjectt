package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarMaintenanceRequest {
	@NotNull
	@NotBlank
	@Positive
	private int carMaintenanceId;
	
	@NotNull
	@Min(1)
	private int carId;
	
	@NotNull
	@NotBlank
	@Size(min = 2, max = 50)
	private String maintenanceDescription;

	private LocalDate returnDate;
}
