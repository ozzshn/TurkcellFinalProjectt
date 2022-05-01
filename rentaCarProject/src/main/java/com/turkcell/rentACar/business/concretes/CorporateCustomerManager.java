package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.constans.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentACar.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	CorporateCustomerDao corporateCustomerDao;
	ModelMapperService modelMapperService;

	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,
				CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
		checkIfExistsByCorporateCustomerId(updateCorporateCustomerRequest.getUserId());
		checkIfExistsByTaxNumber(updateCorporateCustomerRequest.getTaxNumber());
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,
				CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
		checkIfExistsByCorporateCustomerId(deleteCorporateCustomerRequest.getCorporateCustomerId());

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(deleteCorporateCustomerRequest,
				CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER_DELETED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CorporateCustomerListDto>> getAll() throws BusinessException {
		List<CorporateCustomer> result = this.corporateCustomerDao.findAll();

		List<CorporateCustomerListDto> response = result.stream().map(CorporateCustomer -> this.modelMapperService
				.forDto().map(CorporateCustomer, CorporateCustomerListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CorporateCustomerListDto>>(response,
				BusinessMessages.CORPORATE_CUSTOMERS_LISTED_SUCCESSFULLY);
	}

	private void checkIfExistsByCorporateCustomerId(int corporateCustomerId) throws BusinessException {
		if (!this.corporateCustomerDao.existsById(corporateCustomerId)) {
			throw new BusinessException(BusinessMessages.CORPORATE_CUSTOMER_NOT_FOUND);
		}
	}

	private void checkIfExistsByTaxNumber(String taxNumber) throws BusinessException {
		if (!this.corporateCustomerDao.existsByTaxNumber(taxNumber)) {
			throw new BusinessException(BusinessMessages.CORPORATE_CUSTOMER_TAX_NUMBER_NOT_FOUND);
	  }
	}
}