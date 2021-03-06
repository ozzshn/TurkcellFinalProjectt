package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.CarDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CarService {
	
	DataResult<List<CarListDto>> getAll();

	DataResult<CarDto> getById(int id) throws BusinessException;

	DataResult<List<CarListDto>> getByCarDailyPriceLessThanOrEqual(Double carDailyPrice);

	DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize);

	DataResult<List<CarListDto>> getAllSorted(Sort.Direction direction);
	
	Result add(CreateCarRequest createCarRequest) throws BusinessException;

	Result update(UpdateCarRequest updateCarRequest) throws BusinessException;
	
	//Result updateKilometer(int carId, String currentKilometer)throws BusinessException;

	Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException;

	
}
