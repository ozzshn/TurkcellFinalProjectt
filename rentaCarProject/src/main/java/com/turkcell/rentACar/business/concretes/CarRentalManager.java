package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.constans.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.RentalDto;
import com.turkcell.rentACar.business.dtos.RentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.RentalDao;
import com.turkcell.rentACar.entities.concretes.Rental;

import lombok.var;

import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

@Service
public class CarRentalManager implements CarRentalService {

	private RentalDao carRentalDao;
	private CarService carService;
	private CarMaintenanceService carMaintenanceService;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarRentalManager(ModelMapperService modelMapperService, RentalDao carRentalDao,
			@Lazy CarMaintenanceService carMaintenanceService, CarService carService) {
		this.modelMapperService = modelMapperService;
		this.carRentalDao = carRentalDao;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;

	}

	@Override
	public DataResult<List<RentalListDto>> getAll() {

		List<Rental> result = this.carRentalDao.findAll();
		List<RentalListDto> response = result.stream()
				.map(rental -> this.modelMapperService.forDto().map(rental, RentalListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalListDto>>(response, BusinessMessages.RENTALS_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<String> carRentalForIndividualCustomer(CreateCarRentalRequest createCarRentalRequest)
			throws BusinessException {

		checkIfCarExistsById(createCarRentalRequest.getCarId());
		checkIfCarAlreadyInRent(createCarRentalRequest.getCarId());
		checkIfCarInMaintenance(createCarRentalRequest.getCarId());

		Rental rental = this.modelMapperService.forRequest().map(createCarRentalRequest, Rental.class);

		String rentId = createRentId();
		rental.setRentalId(rentId);

		this.carRentalDao.save(rental);

		return new SuccessDataResult<String>(rentId,  BusinessMessages.RENTAL_ADDED_SUCCESSFULLY_FOR_INDIVIDUAL_CUSTOMER);
	}

	@Override
	public DataResult<String> carRentalForCorporateCustomer(CreateCarRentalRequest createCarRentalRequest)
			throws BusinessException {

		checkIfCarExistsById(createCarRentalRequest.getCarId());
		checkIfCarAlreadyInRent(createCarRentalRequest.getCarId());
		checkIfCarInMaintenance(createCarRentalRequest.getCarId());

		Rental rental = this.modelMapperService.forRequest().map(createCarRentalRequest, Rental.class);
		String rentId = createRentId();
		rental.setRentalId(rentId);

		this.carRentalDao.save(rental);

		return new SuccessDataResult<String>(rentId,BusinessMessages.RENTAL_ADDED_SUCCESSFULLY_FOR_CORPORATE_CUSTOMER);
	}

	@Override
	public Result update(UpdateCarRentalRequest updateCarRentalRequest) throws BusinessException {
		checkIfCarExistsById(updateCarRentalRequest.getCarId());
		checkIfCarRentalExistsById(updateCarRentalRequest.getRentalId());

		Rental rental = this.modelMapperService.forRequest().map(updateCarRentalRequest, Rental.class);
		this.carRentalDao.save(rental);

		return new SuccessResult(BusinessMessages.RENTAL_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteCarRentalRequest deleteCarRentalRequest) throws BusinessException {
		checkIfCarRentalExistsById(deleteCarRentalRequest.getRentalId());

		Rental rental = this.modelMapperService.forRequest().map(deleteCarRentalRequest, Rental.class);
		this.carRentalDao.delete(rental);

		return new SuccessResult(BusinessMessages.RENTAL_DELETED_SUCCESSFULLY);	}

	@Override
	public DataResult<List<RentalDto>> getByCarId(int id) throws BusinessException {
		checkIfCarExistsById(id);
		List<Rental> result = this.carRentalDao.getByCar_CarId(id);

		if (result.isEmpty()) {
			return new ErrorDataResult();
		}

		List<RentalDto> response = result.stream()
				.map(rental -> this.modelMapperService.forDto().map(rental, RentalDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalDto>>(response, BusinessMessages.RENTAL_FOR_CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public Rental getByRentalId(String rentalId) {

		return this.carRentalDao.getByRentalId(rentalId);
	}

	@Override
	public DataResult<RentalDto> getById(String id) {

		Rental rental = this.carRentalDao.getById(id);
		RentalDto rentalDto = this.modelMapperService.forDto().map(rental, RentalDto.class);

		return new SuccessDataResult<RentalDto>(rentalDto, BusinessMessages.RENTAL_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<OrderedAdditionalService>> getOrderedAdditionalServicesByRentalId(String rentalId) {
		List<OrderedAdditionalService> result = this.carRentalDao.getOrderedAdditionalServicesByRentalId(rentalId);

		return new SuccessDataResult<List<OrderedAdditionalService>>(result,
				BusinessMessages.ORDERED_ADDITIONAL_SERVICE_FOR_RENTAL_LISTED_SUCCESSFULLY);

	}

	@Override
	public DataResult<Double> calculateCarRentalTotalPrice(String rentalId) {
		Rental rent = this.carRentalDao.getById(rentalId);

		long days = ChronoUnit.DAYS.between(rent.getStartDate(), rent.getReturnDate());

		double price =(rent.getCar().getCarDailyPrice()*days);

		return new SuccessDataResult<Double>(price, BusinessMessages.RENTAL_TOTAL_PRICE_CALCULATED_SUCCESSFULLY);

	}

	@Override
	public Result checkIfReturnCityIsSameForRentalCity(String rentalId) {
		Rental carRent = this.carRentalDao.getById(rentalId);

		if (!carRent.getRentalCity().equals(carRent.getReturnCity())) {

			return new SuccessResult(BusinessMessages.CITIES_ARE_DIFFERENT);
		}

		return new ErrorResult(BusinessMessages.CITIES_ARE_SAME);
	}

	@Override
	public Result checkIfCarAlreadyInRent(int carId) {
		for (Rental rent : this.carRentalDao.getByCar_CarId(carId)) {
			if (!rent.isRentStatus()) {
				return new SuccessResult(BusinessMessages.CAR_NOT_IN_RENTAL);
			}
		}
		return new ErrorResult(BusinessMessages.CAR_ALREADY_IN_RENTAL);
	}

	@Override
	public Result updateFinishKilometer(String rentalId, String currentKilometer) throws BusinessException {
		checkIfCarRentalExistsById(rentalId);
		Rental rental = this.carRentalDao.getById(rentalId);

		rental.setFinishKilometer(currentKilometer);
		rental.getCar().setKilometerInformation(rental.getFinishKilometer());

		this.carRentalDao.save(rental);

		return new SuccessResult(BusinessMessages.FINISH_KILOMETER_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result updateReturnDate(String rentalId, LocalDate newReturnDate) throws BusinessException {
		checkIfCarRentalExistsById(rentalId);
		Rental rental = this.carRentalDao.getById(rentalId);

		rental.setReturnDate(newReturnDate);
		rental.setLateReturnDate(newReturnDate);

		this.carRentalDao.save(rental);

		return new SuccessResult(BusinessMessages.RETURN_DATE_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<Double> calculateLateReturnTotalPriceForCarRental(String rentalId) {
		Rental rent = this.carRentalDao.getById(rentalId);

		if (rent.getLateReturnDate() != null) {
			if (!checkIfDeliveryDateAndRentReturnDateIsDifferent(rent.getLateReturnDate(), rent.getReturnDate())) {

				long extraDays = ChronoUnit.DAYS.between(rent.getLateReturnDate(), rent.getReturnDate()) * (-1);

				return new SuccessDataResult<Double>((rent.getCar().getCarDailyPrice() * extraDays));
			}
		}
		return new ErrorDataResult<Double>(BusinessMessages.RENTAL_LATE_RETURN_PRICE_CALCULATED_FAILED);
	}

	private double calculateExtraPriceByCityDistance(Rental rental) {
		if (rental.getRentalCity().equals(rental.getReturnCity())) {
			return 0;
		}
		return 750;
	}

	private Result checkIfCarInMaintenance(int carId) throws BusinessException {
		var result = this.carMaintenanceService.checkIfCarIsAlreadyInMaintenance(carId);

		if (!result.isSuccess()) {

			return new SuccessResult(BusinessMessages.CAR_IN_RENTAL);
		}
		throw new BusinessException(BusinessMessages.CAR_ALREADY_IN_MAINTENANCE);
	}

	private boolean checkIfCarExistsById(int carId) throws BusinessException {
		if (this.carService.getById(carId) != null) {
			return true;
		}
		return false;
	}

	private boolean checkIfCarRentalExistsById(String rentalId) throws BusinessException {
		if (this.carRentalDao.getById(rentalId) != null) {
			return true;
		}
		return false;
	}

	private boolean checkIfDeliveryDateAndRentReturnDateIsDifferent(LocalDate deliveryDate, LocalDate rentReturnDate) {

		if (deliveryDate != null) {
			if (deliveryDate.equals(rentReturnDate)) {
				return true;
			}
		}
		return false;
	}

	private String createRentId() {
		return UUID.randomUUID().toString();
	}

}
