package com.turkcell.rentACar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.business.dtos.RentalDto;
import com.turkcell.rentACar.business.dtos.RentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.Rental;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

public interface CarRentalService {

	DataResult<String> carRentalForIndividualCustomer(CreateCarRentalRequest createCarRentalRequest)
			throws BusinessException;

	DataResult<String> carRentalForCorporateCustomer(CreateCarRentalRequest createCarRentalRequest)
			throws BusinessException;

	Result update(UpdateCarRentalRequest updateCarRentalRequest) throws BusinessException;
	
	Result updateFinishKilometer(String rentalId, String currentKilometer) throws BusinessException;
	
	Result updateReturnDate(String rentalId, LocalDate newReturnDate) throws BusinessException;
	
	Result delete(DeleteCarRentalRequest deleteCarRentalRequest) throws BusinessException;
	
	DataResult<RentalDto> getById(String id);
	DataResult<List<RentalListDto>> getAll();
	DataResult<List<RentalDto>> getByCarId(int id) throws BusinessException;

    Rental getByRentalId(String rentalId);

	DataResult<Double> calculateCarRentalTotalPrice(String rentalId);
	DataResult<Double> calculateLateReturnTotalPriceForCarRental(String rentalId);
	
	DataResult<List<OrderedAdditionalService>> getOrderedAdditionalServicesByRentalId(String rentalId);

	Result checkIfReturnCityIsSameForRentalCity(String rentalId);

	Result checkIfCarAlreadyInRent(int carId);

}
