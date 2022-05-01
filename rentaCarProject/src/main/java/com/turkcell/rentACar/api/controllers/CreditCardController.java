package com.turkcell.rentACar.api.controllers;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CreditCardService;
import com.turkcell.rentACar.business.requests.creates.CreateCreditCardRequest;
import com.turkcell.rentACar.business.dtos.CreditCardDto;
import com.turkcell.rentACar.business.dtos.CreditCardListDto;
import com.turkcell.rentACar.business.requests.deletes.DeleteCreditCardRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCreditCardRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/creditCards")
public class CreditCardController {
	private CreditCardService creditCardService;

	@Autowired
	public CreditCardController(CreditCardService creditCardService) {
		this.creditCardService = creditCardService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCreditCardRequest createCreditCardRequest) {
		return this.creditCardService.add(createCreditCardRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody @Valid UpdateCreditCardRequest updateCreditCardRequest) {
		return this.creditCardService.update(updateCreditCardRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCreditCardRequest deleteCreditCardRequest) {
		return this.creditCardService.delete(deleteCreditCardRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<CreditCardListDto>> getAll() {
		return this.creditCardService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<CreditCardDto> getById(@RequestParam int id) {
		return this.creditCardService.getById(id);
	}

	
}

