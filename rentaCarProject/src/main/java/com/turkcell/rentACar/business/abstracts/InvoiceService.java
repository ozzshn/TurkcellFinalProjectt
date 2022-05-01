package com.turkcell.rentACar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.business.dtos.InvoiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteInvoiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

public interface InvoiceService {
	 
	Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;

	Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException;

	Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException;

	DataResult<List<InvoiceListDto>> getAll() throws BusinessException;

	DataResult<InvoiceListDto> getByRentalId(String rentalId) throws BusinessException;

	DataResult<List<OrderedAdditionalService>> getOrderedAdditionalServiceByRentalId(String rentalId);
	
	DataResult<List<InvoiceListDto>> getInvoiceByUserId(int userId) throws BusinessException;

	DataResult<List<InvoiceListDto>> getAllInvoicesByBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate);



}
