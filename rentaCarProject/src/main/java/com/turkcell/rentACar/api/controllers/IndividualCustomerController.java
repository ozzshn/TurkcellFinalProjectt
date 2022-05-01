package com.turkcell.rentACar.api.controllers;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.dtos.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomerController {

	private IndividualCustomerService individualCustomerService;

	public IndividualCustomerController(IndividualCustomerService individualCustomerService) {
		 this.individualCustomerService = individualCustomerService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody  CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
		return this.individualCustomerService.add(createIndividualCustomerRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody  UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody  DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<IndividualCustomerListDto>> getAll() throws BusinessException{
		return this.individualCustomerService.getAll();
		
	}

}
