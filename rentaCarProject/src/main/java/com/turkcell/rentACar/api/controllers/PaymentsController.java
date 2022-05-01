package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.api.models.MakePaymentModel;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.dtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.deletes.DeletePaymentRequest;
import com.turkcell.rentACar.business.requests.updates.UpdatePaymentRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

	private PaymentService paymentService;

	@Autowired
	public PaymentsController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@PostMapping("/addPaymentForIndividualCustomer")
	public Result addPaymentForIndividualCustomer(@RequestBody @Valid MakePaymentModel makePaymentModel)
			throws BusinessException {
		return this.paymentService.addPaymentForIndividualCustomer(makePaymentModel);
	}

	@PostMapping("/addPaymentForCorporateCustomer")
	public Result addPaymentForCorporateCustomer(@RequestBody @Valid MakePaymentModel makePaymentModel)
			throws BusinessException {
		return this.paymentService.addPaymentForCorporateCustomer(makePaymentModel);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody @Valid DeletePaymentRequest deletePaymentRequest) throws BusinessException {
		return this.paymentService.delete(deletePaymentRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody @Valid UpdatePaymentRequest updatePaymentRequest) throws BusinessException {
		return this.paymentService.update(updatePaymentRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<PaymentListDto>> getAll() {
		return paymentService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<PaymentDto> getById(int paymentId) throws BusinessException {
		return paymentService.getById(paymentId);
	}

}
