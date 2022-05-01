package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.dtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/CorporateCustomers")
public class CorporateCustomersController {

	private CorporateCustomerService CorporateCustomerService;

	@Autowired
	public CorporateCustomersController(CorporateCustomerService CorporateCustomerService) {
		 this.CorporateCustomerService = CorporateCustomerService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
		return this.CorporateCustomerService.add(createCorporateCustomerRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
		return this.CorporateCustomerService.update(updateCorporateCustomerRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
		return this.CorporateCustomerService.delete(deleteCorporateCustomerRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<CorporateCustomerListDto>> getAll() throws BusinessException{
		return this.CorporateCustomerService.getAll();
		
	}

}

