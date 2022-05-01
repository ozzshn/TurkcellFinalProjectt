package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.api.models.MakePaymentModel;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.CreditCardService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.constans.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.creates.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.creates.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.deletes.DeletePaymentRequest;
import com.turkcell.rentACar.business.requests.updates.UpdatePaymentRequest;
import com.turkcell.rentACar.core.service.abstracts.FakeBankPaymentServiceAdapter;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.PaymentDao;

import com.turkcell.rentACar.entities.concretes.Payment;

import lombok.var;

@Service
public class PaymentManager implements PaymentService {

	private ModelMapperService modelMapperService;
	private PaymentDao paymentDao;
	private InvoiceService invoiceService;
	private CarRentalService carRentalService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private FakeBankPaymentServiceAdapter fakeBankPaymentServiceAdapter;

	@Autowired
	public PaymentManager(ModelMapperService modelMapperService, InvoiceService invoiceService,
			CarRentalService carRentalService, OrderedAdditionalServiceService orderedAdditionalServiceService,
			CreditCardService creditCardService, PaymentDao paymentDao,
			@Qualifier("isbank") FakeBankPaymentServiceAdapter fakeBankPaymentServiceAdapter) {
		this.modelMapperService = modelMapperService;
		this.paymentDao = paymentDao;
		this.invoiceService = invoiceService;
		this.carRentalService = carRentalService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.fakeBankPaymentServiceAdapter = fakeBankPaymentServiceAdapter;
	}

	@Override
	public Result addPaymentForIndividualCustomer(MakePaymentModel makePaymentModel) throws BusinessException {

		Payment payment = this.modelMapperService.forRequest().map(makePaymentModel.getCreatePaymentRequest(),
				Payment.class);


		checkIfPaymentIsSuccess(payment);
		runMakePaymentSuccessorForIndividualCustomer(makePaymentModel);

		return new SuccessResult(BusinessMessages.PAYMENT_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result addPaymentForCorporateCustomer(MakePaymentModel makePaymentModel) throws BusinessException {

		Payment payment = this.modelMapperService.forRequest().map(makePaymentModel.getCreatePaymentRequest(),
				Payment.class);

		checkIfPaymentIsSuccess(payment);
		runMakePaymentSuccessorForCorporateCustomer(makePaymentModel);

		return new SuccessResult(BusinessMessages.PAYMENT_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException {
		paymentDao.deleteById(deletePaymentRequest.getPaymentId());

		return new SuccessResult(BusinessMessages.PAYMENT_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdatePaymentRequest updatePaymentRequest) throws BusinessException {

		checkIfPaymentExists(updatePaymentRequest.getPaymentId());

		Payment payment = this.modelMapperService.forRequest().map(updatePaymentRequest, Payment.class);
		this.paymentDao.save(payment);

		return new SuccessResult(BusinessMessages.PAYMENT_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<PaymentListDto>> getAll() {
		var result = this.paymentDao.findAll();

		List<PaymentListDto> response = result.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, PaymentListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<PaymentListDto>>(response, BusinessMessages.PAYMENTS_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<PaymentDto> getById(int paymentId) throws BusinessException {

		Payment result = this.paymentDao.getById(paymentId);
		PaymentDto response = this.modelMapperService.forDto().map(result, PaymentDto.class);

		return new SuccessDataResult<PaymentDto>(response, BusinessMessages.PAYMENT_LISTED_SUCCESSFULLY);
	}

	@Transactional
	public void runMakePaymentSuccessorForIndividualCustomer(MakePaymentModel makePaymentModel)
			throws BusinessException {
		String rentalId = this.carRentalService
				.carRentalForIndividualCustomer(makePaymentModel.getCreateCarRentalRequest()).getData();

		CreateInvoiceRequest createInvoiceRequest = makePaymentModel.getCreateInvoiceRequest();
		createInvoiceRequest.setRentalId(rentalId);
		this.invoiceService.add(createInvoiceRequest);

		for (CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest : makePaymentModel
				.getCreateOrderedAdditionalServiceRequests()) {

			createOrderedAdditionalServiceRequest.setRentalId(rentalId);

			this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
		}

		CreatePaymentRequest createPaymentRequest = makePaymentModel.getCreatePaymentRequest();
		createPaymentRequest.setRentalId(rentalId);

		this.paymentDao.save(this.modelMapperService.forRequest()
				.map(this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class), Payment.class));
	}

	@Transactional
	public void runMakePaymentSuccessorForCorporateCustomer(MakePaymentModel makePaymentModel)
			throws BusinessException {
		String rentalId = this.carRentalService
				.carRentalForCorporateCustomer(makePaymentModel.getCreateCarRentalRequest()).getData();

		CreateInvoiceRequest createInvoiceRequest = makePaymentModel.getCreateInvoiceRequest();
		createInvoiceRequest.setRentalId(rentalId);
		this.invoiceService.add(createInvoiceRequest);

		for (CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest : makePaymentModel
				.getCreateOrderedAdditionalServiceRequests()) {

			createOrderedAdditionalServiceRequest.setRentalId(rentalId);

			this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
		}

		CreatePaymentRequest createPaymentRequest = makePaymentModel.getCreatePaymentRequest();
		createPaymentRequest.setRentalId(rentalId);

		this.paymentDao.save(this.modelMapperService.forRequest()
				.map(this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class), Payment.class));
	}


	private void checkIfPaymentExists(int paymentId) throws BusinessException {
		if (this.paymentDao.getByPaymentId(paymentId) == null) {
			throw new BusinessException(BusinessMessages.PAYMENT_NOT_FOUND);
		}
	}

	private void checkIfPaymentIsSuccess(Payment payment) throws BusinessException {
		if (!this.fakeBankPaymentServiceAdapter.payment(payment)) {
			throw new BusinessException(BusinessMessages.PAYMENT_FAILED);
		}
	}

}
