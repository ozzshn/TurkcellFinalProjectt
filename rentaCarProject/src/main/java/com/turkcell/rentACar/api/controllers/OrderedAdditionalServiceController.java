package com.turkcell.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/OrderedAdditionalServices")
public class OrderedAdditionalServiceController {

	private OrderedAdditionalServiceService orderedAdditionalServiceService;

	public OrderedAdditionalServiceController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}

	@GetMapping("/getAll")
	public DataResult<List<OrderedAdditionalServiceListDto>> getAll() {
		return this.orderedAdditionalServiceService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest)
			throws BusinessException {
		return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest)
			throws BusinessException {
		return this.orderedAdditionalServiceService.update(updateOrderedAdditionalServiceRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest)
			throws BusinessException {
		return this.orderedAdditionalServiceService.delete(deleteOrderedAdditionalServiceRequest);
	}

	@GetMapping("/getByRentalId")
	DataResult<List<OrderedAdditionalServiceListDto>> getByRentalId(@RequestParam String rentalId) {
		return this.orderedAdditionalServiceService.getByRentalId(rentalId);
	}

	@PostMapping("/calculateOrderedAdditionalServicePrice")
	DataResult<Double> calculateOrderedAdditionalServicePrice(@RequestParam String rentalId) {
		return this.orderedAdditionalServiceService.calculateOrderedAdditionalServicePrice(rentalId);
	}

}
