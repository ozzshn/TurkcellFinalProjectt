package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.business.constans.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CarDamageListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarDamageRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDamageDao;
import com.turkcell.rentACar.entities.concretes.CarDamage;

@Service
public class CarDamageManager implements CarDamageService {

	private CarDamageDao carDamageDao;
	private ModelMapperService modelMapperService;

	public CarDamageManager(CarDamageDao carDamageDao, ModelMapperService modelMapperService) {
		this.carDamageDao = carDamageDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamageRequest) {
		CarDamage carDamage = this.modelMapperService.forRequest().map(createCarDamageRequest, CarDamage.class);

		this.carDamageDao.save(carDamage);

		return new SuccessResult(BusinessMessages.CAR_DAMAGE_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException {
		checkIfExistByCarId(updateCarDamageRequest.getCarId());
		checkIfExistByCarDamageId(updateCarDamageRequest.getCarDamageId());


		CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);

		this.carDamageDao.save(carDamage);

		return new SuccessResult(BusinessMessages.CAR_DAMAGE_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteCarDamageRequest deleteCarDamageRequest) throws BusinessException {
		checkIfExistByCarDamageId(deleteCarDamageRequest.getCarDamageId());

		CarDamage carDamage = this.modelMapperService.forRequest().map(deleteCarDamageRequest, CarDamage.class);

		this.carDamageDao.delete(carDamage);

		return new SuccessResult(BusinessMessages.CAR_DAMAGE_DELETED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarDamageListDto>> getAll() {
		List<CarDamage> result=this.carDamageDao.findAll();
		List<CarDamageListDto> response=result.stream()
				.map(carDamage->this.modelMapperService.forDto().map(carDamage, CarDamageListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarDamageListDto>>(response,
				BusinessMessages.CAR_DAMAGES_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<CarDamageListDto> getById(int id) throws BusinessException {
		checkIfExistByCarDamageId(id);
		
		CarDamage carDamage=this.carDamageDao.getById(id);
		CarDamageListDto carDamageListDto=this.modelMapperService.forDto()
				.map(carDamage, CarDamageListDto.class);
		
		return new SuccessDataResult<CarDamageListDto>(carDamageListDto,
				BusinessMessages.CAR_DAMAGE_LISTED_SUCCESSFULLY);
		
	}
	
	private void checkIfExistByCarId(int carId) throws BusinessException {
		if (!this.carDamageDao.existsById(carId)) {
			throw new BusinessException(BusinessMessages.CAR_NOT_FOUND);
		}
	}
	
	private void checkIfExistByCarDamageId(int carDamageId) throws BusinessException {
		if (!this.carDamageDao.existsById(carDamageId)) {
			throw new BusinessException(BusinessMessages.CAR_DAMAGE_NOT_FOUND);
		}
	}



}
