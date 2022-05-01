package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.constans.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	IndividualCustomerDao individualCustomerDao;
	ModelMapperService modelMapperService;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao,
			ModelMapperService modelMapperService) {
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
		checkIfNationalIdentityExists(createIndividualCustomerRequest.getNationalIdentity());

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(createIndividualCustomerRequest, IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);

		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(updateIndividualCustomerRequest, IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);

		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(deleteIndividualCustomerRequest, IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);

		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_DELETED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<IndividualCustomerListDto>> getAll() throws BusinessException {
		List<IndividualCustomer> result = this.individualCustomerDao.findAll();

		List<IndividualCustomerListDto> response = result.stream().map(individualCustomer -> this.modelMapperService
				.forDto().map(individualCustomer, IndividualCustomerListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<IndividualCustomerListDto>>(response,
				BusinessMessages.INDIVIDUAL_CUSTOMERS_LISTED_SUCCESSFULLY);
	}

	private void checkIfNationalIdentityExists(String nationalIdentity) throws BusinessException {
		if (this.individualCustomerDao.existsByNationalIdentity(nationalIdentity)) {
			throw new BusinessException(BusinessMessages.NATIONAL_IDENTITY_NOT_VALID);
		}
	}

}
