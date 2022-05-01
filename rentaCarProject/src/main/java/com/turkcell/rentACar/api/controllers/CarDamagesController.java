package com.turkcell.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.business.dtos.CarDamageListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarDamageRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carDamages")
public class CarDamagesController {

	private CarDamageService carDamageService;
	
	public CarDamagesController(CarDamageService carDamageService) {
		this.carDamageService=carDamageService;
	}
	
    @PostMapping("/add")
	public Result add(CreateCarDamageRequest createCarDamageRequest) {
		return this.carDamageService.add(createCarDamageRequest);
	}
    
    @PostMapping("/update")
	public Result update(UpdateCarDamageRequest updateCarDamageRequest)throws BusinessException{
    	return this.carDamageService.update(updateCarDamageRequest);
    }

    @PostMapping("/delete")
	public Result delete(DeleteCarDamageRequest deleteCarDamageRequest)throws BusinessException{
    	return this.carDamageService.delete(deleteCarDamageRequest);
    }

    @GetMapping("/getAll")
	public DataResult<List<CarDamageListDto>> getAll(){
    return this.carDamageService.getAll();
    }
	
    @GetMapping("/getById")
	public DataResult<CarDamageListDto> getById(int id)throws BusinessException{
    	return this.carDamageService.getById(id);
    }
	
	
}
