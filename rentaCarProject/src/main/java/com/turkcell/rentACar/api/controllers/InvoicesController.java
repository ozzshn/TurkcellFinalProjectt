package com.turkcell.rentACar.api.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.dtos.InvoiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteInvoiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {
	private InvoiceService invoicesService;

	@Autowired
	public InvoicesController(InvoiceService invoicesService) {
		this.invoicesService = invoicesService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
		return this.invoicesService.add(createInvoiceRequest);

	}

	@PostMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException {
		return this.invoicesService.delete(deleteInvoiceRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody @Valid UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
		return this.invoicesService.update(updateInvoiceRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<InvoiceListDto>> getAll() throws BusinessException {
		return this.invoicesService.getAll();
	}

	@GetMapping("/getInvoicesByUserId")
	public DataResult<List<InvoiceListDto>> getInvoicesByUserId(@RequestParam int userId) throws BusinessException {
		return this.invoicesService.getInvoiceByUserId(userId);
	}

	@GetMapping("/getAllInvoicesByBetweenStartDateAndEndDate")
	public DataResult<List<InvoiceListDto>> getAllInvoicesByBetweenStartDateAndEndDate(LocalDate startDate,
			LocalDate endDate) {
		return this.invoicesService.getAllInvoicesByBetweenStartDateAndEndDate(startDate, endDate);
	}

}
