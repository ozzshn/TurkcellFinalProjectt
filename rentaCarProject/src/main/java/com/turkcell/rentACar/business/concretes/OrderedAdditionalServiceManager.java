package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.constans.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {

	private OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private ModelMapperService modelMapperService;
	private CarRentalService carRentalService;

	@Autowired
	public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao,
			ModelMapperService modelMapperService, @Lazy CarRentalService carRentalService) {
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.modelMapperService = modelMapperService;
		this.carRentalService = carRentalService;
	}

	@Override
	public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest)
			throws BusinessException {

		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
				.map(createOrderedAdditionalServiceRequest, OrderedAdditionalService.class);

		orderedAdditionalService.setOrderedAdditionalServiceId(0);
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);

		return new SuccessResult(BusinessMessages.ORDERED_ADDITIONAL_SERVICE_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest)
			throws BusinessException {

		checkIfCarRentalExistsById(updateOrderedAdditionalServiceRequest.getRentalId());
		checkIfExistByOrderedAdditionalServiceId(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());

		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
				.map(updateOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);

		return new SuccessResult(BusinessMessages.ORDERED_ADDITIONAL_SERVICE_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest)
			throws BusinessException {

		checkIfExistByOrderedAdditionalServiceId(deleteOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());

		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
				.map(deleteOrderedAdditionalServiceRequest, OrderedAdditionalService.class);

		this.orderedAdditionalServiceDao.delete(orderedAdditionalService);
		return new SuccessResult(BusinessMessages.ORDERED_ADDITIONAL_SERVICE_DELETED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<OrderedAdditionalServiceListDto>> getAll() {
		List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.findAll();

		List<OrderedAdditionalServiceListDto> response = result.stream()
				.map(orderedOrderedAdditionalService -> this.modelMapperService.forDto()
						.map(orderedOrderedAdditionalService, OrderedAdditionalServiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<OrderedAdditionalServiceListDto>>(response,
				BusinessMessages.ORDERED_ADDITIONAL_SERVICES_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<OrderedAdditionalServiceListDto>> getByRentalId(String rentalId) {

		List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.getByRental_RentalId(rentalId);

		List<OrderedAdditionalServiceListDto> response = result.stream()
				.map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService,
						OrderedAdditionalServiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<OrderedAdditionalServiceListDto>>(response,
				BusinessMessages.ORDERED_ADDITIONAL_SERVICE_FOR_RENTAL_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<Double> calculateOrderedAdditionalServicePrice(String rentalId) {

		double orderedAdditionalServiceTotalPrice = 0;

		List<OrderedAdditionalServiceListDto> result = this.getByRentalId(rentalId).getData();

		List<OrderedAdditionalService> orderedAdditionalServiceList = result.stream()
				.map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService,
						OrderedAdditionalService.class))
				.collect(Collectors.toList());

		for (OrderedAdditionalService orderedAdditionalService : orderedAdditionalServiceList) {

			orderedAdditionalServiceTotalPrice += (orderedAdditionalService.getOrderedAdditionalServiceTotal()
					* orderedAdditionalService.getAdditionalService().getAdditionalServiceDailyPrice());

		}
		return new SuccessDataResult<Double>(orderedAdditionalServiceTotalPrice,
				BusinessMessages.ORDERED_ADDITIONAL_SERVICE_PRICE_CALCULATED_SUCCESSFULLY);

	}

	@Override
	public Result checkIfRentalExists(String rentalId) {
		if (this.orderedAdditionalServiceDao.existsByRental_RentalId(rentalId)) {
			return new SuccessResult(BusinessMessages.RENTAL_ALREADY_EXISTS_IN_ORDERED_ADDITIONAL_SERVICE);
		}

		return new ErrorResult(BusinessMessages.RENTAL_NOT_EXISTS_IN_ORDERED_ADDITIONAL_SERVICE);
	}
	
	private void checkIfExistByOrderedAdditionalServiceId(int orderedAdditionalServiceId) throws BusinessException {
		if (!this.orderedAdditionalServiceDao.existsById(orderedAdditionalServiceId)) {
			throw new BusinessException(BusinessMessages.ORDERED_ADDITIONAL_SERVICE_NOT_FOUND);
		}
	}

	private void checkIfCarRentalExistsById(String rentalId) throws BusinessException {
		this.carRentalService.getById(rentalId);
	}

}
