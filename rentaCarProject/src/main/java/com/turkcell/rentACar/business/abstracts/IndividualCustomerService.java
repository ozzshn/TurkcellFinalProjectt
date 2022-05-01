package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface IndividualCustomerService {

	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException;

	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException;

	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException;

	DataResult<List<IndividualCustomerListDto>> getAll() throws BusinessException;

}
