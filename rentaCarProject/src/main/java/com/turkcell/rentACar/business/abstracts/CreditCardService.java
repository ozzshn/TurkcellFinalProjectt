package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.CreditCardDto;
import com.turkcell.rentACar.business.dtos.CreditCardListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCreditCardRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCreditCardRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CreditCardService {
	
	Result add(CreateCreditCardRequest createCreditCardRequest);

	Result update(UpdateCreditCardRequest updateCreditCardRequest);

	Result delete(DeleteCreditCardRequest deleteCreditCardRequest);

	DataResult<List<CreditCardListDto>> getAll();

	DataResult<CreditCardDto> getById(int creditCardId);
	
}
