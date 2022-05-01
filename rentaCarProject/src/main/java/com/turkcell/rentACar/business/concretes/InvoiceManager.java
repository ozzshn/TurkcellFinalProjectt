package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.constans.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.InvoiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteInvoiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentACar.entities.concretes.Invoice;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

@Service
public class InvoiceManager implements InvoiceService {

	private InvoiceDao invoiceDao;
	private CarRentalService carRentalService;
	private ModelMapperService modelMapperService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, CarRentalService carRentalService,
			ModelMapperService modelMapperService, OrderedAdditionalServiceService orderedAdditionalServiceService) {
		this.invoiceDao = invoiceDao;
		this.carRentalService = carRentalService;
		this.modelMapperService = modelMapperService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

		if (checkIfDeliveryDateAndRentReturnDateIsDifferent(invoice.getRental().getRentalId())) {

			invoice.setTotalPrice(calculateLateDeliveryPrice(invoice.getRental().getRentalId()));
			invoice.setInvoiceCreationDate(LocalDate.now());
			
		} else {

			invoice.setTotalPrice(calculateTotalPrice(invoice));
			invoice.setInvoiceCreationDate(LocalDate.now());
		}

		this.invoiceDao.save(invoice);

		return new SuccessResult(BusinessMessages.INVOICE_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException {
		checkIfExistByInvoiceId(deleteInvoiceRequest.getInvoiceId());

		Invoice invoice = this.modelMapperService.forRequest().map(deleteInvoiceRequest, Invoice.class);
		this.invoiceDao.delete(invoice);

		return new SuccessResult(BusinessMessages.INVOICE_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {

		checkIfExistByInvoiceId(updateInvoiceRequest.getInvoiceId());

		Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);
		invoice.setTotalPrice(calculateTotalPrice(invoice));

		this.invoiceDao.save(invoice);

		return new SuccessResult(BusinessMessages.INVOICE_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<InvoiceListDto>> getAll() throws BusinessException {
		List<Invoice> result = this.invoiceDao.findAll();
		List<InvoiceListDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceListDto>>(response, BusinessMessages.INVOICES_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<InvoiceListDto> getByRentalId(String rentalId) throws BusinessException {
		checkIfRentalExists(rentalId);

		Invoice result = this.invoiceDao.getByRental_RentalId(rentalId);
		InvoiceListDto response = this.modelMapperService.forDto().map(result, InvoiceListDto.class);

		return new SuccessDataResult<InvoiceListDto>(response, BusinessMessages.INVOICE_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<OrderedAdditionalService>> getOrderedAdditionalServiceByRentalId(String rentalId) {

		List<OrderedAdditionalService> result = this.carRentalService.getOrderedAdditionalServicesByRentalId(rentalId)
				.getData();

		return new SuccessDataResult<List<OrderedAdditionalService>>(result,
				BusinessMessages.INVOICE_LISTED_SUCCESSFULLY);

	}

	@Override
	public DataResult<List<InvoiceListDto>> getInvoiceByUserId(int userId) throws BusinessException {
		checkIfExistsByUserId(userId);

		List<Invoice> invoices = this.invoiceDao.getByUser_UserId(userId);

		List<InvoiceListDto> response = invoices.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceListDto>>(response, BusinessMessages.USERS_INVOICES_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<InvoiceListDto>> getAllInvoicesByBetweenStartDateAndEndDate(LocalDate startDate,
			LocalDate endDate) {
		List<Invoice> result = this.invoiceDao.getAllByBetweenStartDateAndEndDate(startDate, endDate);
		List<InvoiceListDto> response = result.stream()
				.map(invoice -> modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceListDto>>(response,
				BusinessMessages.INVOICE_BETWEEN_START_DATE_AND_END_DATE_LISTED_SUCCESSFULLY);
	}

	private double calculateTotalPrice(Invoice invoice) {
		double totalPrice = calculateCarRentalTotalPrice(invoice.getRental().getRentalId())
				+ calculateIfCityIsDifferentExtraPrice(invoice.getRental().getRentalId())
				+ calculateOrderedAdditionalPrice(invoice.getRental().getRentalId());

		return totalPrice;
	}

	private double calculateCarRentalTotalPrice(String rentalId) {
		return this.carRentalService.calculateCarRentalTotalPrice(rentalId).getData();
	}

	private double calculateIfCityIsDifferentExtraPrice(String rentalId) {
		if (this.carRentalService.checkIfReturnCityIsSameForRentalCity(rentalId).isSuccess()) {
			return 750;
		}
		return 0;
	}

	private double calculateOrderedAdditionalPrice(String rentalId) {
		if (checkIfRentalHasOrderedAdditionalService(rentalId)) {
			return this.orderedAdditionalServiceService.calculateOrderedAdditionalServicePrice(rentalId).getData();
		}
		return 0;
	}

	private double calculateLateDeliveryPrice(String rentalId) {

		if (this.carRentalService.calculateLateReturnTotalPriceForCarRental(rentalId).isSuccess()) {
			return this.carRentalService.calculateLateReturnTotalPriceForCarRental(rentalId).getData()
					+ calculateOrderedAdditionalPrice(rentalId);
		}

		return 0;
	}

	private void checkIfRentalExists(String rentalId) throws BusinessException {

		if (!this.invoiceDao.existsByRental_RentalId(rentalId)) {
			throw new BusinessException(BusinessMessages.RENTAL_NOT_FOUND);
		}
	}

	private boolean checkIfDeliveryDateAndRentReturnDateIsDifferent(String rentalId) {
		return this.carRentalService.calculateLateReturnTotalPriceForCarRental(rentalId).isSuccess();
	}

	private void checkIfExistsByUserId(int userId) throws BusinessException {
		if (!this.invoiceDao.existsByUser_UserId(userId)) {
			throw new BusinessException(BusinessMessages.USER_NOT_FOUND);
		}
	}

	private void checkIfExistByInvoiceId(int invoiceId) throws BusinessException {
		if (!this.invoiceDao.existsById(invoiceId)) {
			throw new BusinessException(BusinessMessages.INVOICE_NOT_FOUND);
		}
	}

	private boolean checkIfRentalHasOrderedAdditionalService(String rentalId) {

		if (this.orderedAdditionalServiceService.checkIfRentalExists(rentalId).isSuccess()) {
			return true;
		}
		return false;
	}

}
