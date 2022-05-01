package com.turkcell.rentACar.api.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.dtos.RentalDto;
import com.turkcell.rentACar.business.dtos.RentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carRental")
public class CarRentalController {
	private CarRentalService carRentalService;

	public CarRentalController(CarRentalService carRentalService) {
		this.carRentalService = carRentalService;
	}

	@GetMapping("/getAll")
	DataResult<List<RentalListDto>> getAll() {
		return carRentalService.getAll();
	}

	@GetMapping("/getByCarId")
	DataResult<List<RentalDto>> getByCarId(@RequestParam int id) throws BusinessException {
		return carRentalService.getByCarId(id);
	}

	@PostMapping("/carRentaladdForIndividualCustomer")
	DataResult<String> carRentalForIndividualCustomer(@RequestBody CreateCarRentalRequest createCarRentalRequest)
			throws BusinessException {
		return carRentalService.carRentalForIndividualCustomer(createCarRentalRequest);
	}

	@PostMapping("/carRentaladdForCorporateCustomer")
	DataResult<String> carRentalForCorporateCustomer(@RequestBody CreateCarRentalRequest createCarRentalRequest)
			throws BusinessException {
		return carRentalService.carRentalForCorporateCustomer(createCarRentalRequest);
	}

	@DeleteMapping("/delete")
	Result delete(@RequestBody DeleteCarRentalRequest deleteCarRentalRequest) throws BusinessException {
		return carRentalService.delete(deleteCarRentalRequest);
	}

	@PostMapping("/update")
	Result update(@RequestBody UpdateCarRentalRequest updateCarRentalRequest) throws BusinessException {
		return carRentalService.update(updateCarRentalRequest);
	}

	@PostMapping("/updateFinishKilometer")
	Result updateFinishKilometer(String rentalId, String currentKilometer) throws BusinessException {
		return carRentalService.updateFinishKilometer(rentalId, currentKilometer);

	}

	@PostMapping("/updateReturnDate")
	Result updateReturnDate(String rentalId, LocalDate newReturnDate) throws BusinessException {
		return carRentalService.updateReturnDate(rentalId, newReturnDate);

	}

}
