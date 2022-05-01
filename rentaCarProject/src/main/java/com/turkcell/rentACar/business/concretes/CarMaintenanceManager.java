package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.constans.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.CarDao;
import com.turkcell.rentACar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACar.entities.concretes.CarMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	private CarRentalService carRentalService;
	private CarService carService;

	@Autowired
	@Lazy
	public CarMaintenanceManager(ModelMapperService modelMapperService, CarDao carDao,
			CarMaintenanceDao carMaintenanceDao, CarRentalService carRentalService) {
		this.modelMapperService = modelMapperService;
		this.carMaintenanceDao = carMaintenanceDao;
		this.carRentalService = carRentalService;

	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getAll() {
		List<CarMaintenance> result = this.carMaintenanceDao.findAll();
		List<CarMaintenanceListDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarMaintenanceListDto>>(response, BusinessMessages.CAR_MAINTENANCES_LISTED_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {

		checkIfCarExistsById(createCarMaintenanceRequest.getCarId());
		checkIfReturnDateBeforeLocalDateNow(createCarMaintenanceRequest.getReturnDate());
		checkIfCarIsAlreadyInRent(createCarMaintenanceRequest.getCarId());

		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
				CarMaintenance.class);

		this.carMaintenanceDao.save(carMaintenance);

		return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		checkIfCarExistsById(updateCarMaintenanceRequest.getCarId());
		checkIfExistByMaintenanceId(updateCarMaintenanceRequest.getCarId());
		checkIfReturnDateBeforeLocalDateNow(updateCarMaintenanceRequest.getReturnDate());

		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,
				CarMaintenance.class);
		this.carMaintenanceDao.save(carMaintenance);

		return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
		checkIfExistByMaintenanceId(deleteCarMaintenanceRequest.getCarMaintenanceId());

		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(deleteCarMaintenanceRequest,
				CarMaintenance.class);
		this.carMaintenanceDao.deleteById(carMaintenance.getCarMaintenanceId());

		return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_DELETED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getByCarId(int id) throws BusinessException {
		checkIfExistByMaintenanceId(id);

		List<CarMaintenance> carMaintenanceList = carMaintenanceDao.getAllByCar_CarId(id);
		List<CarMaintenanceListDto> response = carMaintenanceList.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarMaintenanceListDto>>(response, BusinessMessages.CAR_MAINTENANCES_FOR_CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public Result checkIfCarIsAlreadyInMaintenance(int carId) {
		for (CarMaintenance carMaintenance : this.carMaintenanceDao.getAllByCar_CarId(carId)) {
			if (carMaintenance.getReturnDate() != null) {
				return new SuccessResult(BusinessMessages.CAR_NOT_IN_MAINTENANCE);
			}
		}
		return new ErrorResult(BusinessMessages.CAR_ALREADY_IN_MAINTENANCE);
	}

	private void checkIfExistByMaintenanceId(int maintenanceId) throws BusinessException {
		if (!this.carMaintenanceDao.existsById(maintenanceId)) {
			throw new BusinessException(BusinessMessages.CAR_MAINTENANCE_NOT_FOUND);
		}
	}

	private boolean checkIfCarExistsById(int carId) throws BusinessException {
		if (this.carService.getById(carId) != null) {
			return true;
		}
		return false;
	}

	private void checkIfCarIsAlreadyInRent(int carId) throws BusinessException {
		if (!checkIfCarExistsById(carId)) {
			if (!this.carRentalService.checkIfCarAlreadyInRent(carId).isSuccess()) {
				throw new BusinessException(BusinessMessages.CAR_ALREADY_IN_RENTAL);
			}
		}
	}

	private void checkIfReturnDateBeforeLocalDateNow(LocalDate returnDate) throws BusinessException {
		if (returnDate != null) {
			if (returnDate.isBefore(LocalDate.now())) {
				throw new BusinessException(BusinessMessages.RETURN_DATE_CANNOT_BEFORE_TODAY);
			}
		}

	}

}
