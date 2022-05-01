package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CreditCardService;
import com.turkcell.rentACar.business.constans.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CreditCardDto;
import com.turkcell.rentACar.business.dtos.CreditCardListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCreditCardRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCreditCardRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CreditCardDao;
import com.turkcell.rentACar.entities.concretes.CreditCard;

@Service
public class CreditCardManager implements CreditCardService {
	private CreditCardDao creditCardDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao, ModelMapperService modelMapperService) {
		this.creditCardDao = creditCardDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCreditCardRequest createCreditCardRequest) {

		CreditCard creditCard = this.modelMapperService.forRequest().map(createCreditCardRequest, CreditCard.class);

		this.creditCardDao.save(creditCard);

		return new SuccessResult(BusinessMessages.CREDIT_CARD_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCreditCardRequest updateCreditCardRequest) {

		CreditCard creditCard = this.modelMapperService.forRequest().map(updateCreditCardRequest, CreditCard.class);

		this.creditCardDao.save(creditCard);

		return new SuccessResult(BusinessMessages.CREDIT_CARD_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteCreditCardRequest deleteCreditCardRequest) {

		CreditCard creditCard = this.modelMapperService.forRequest().map(deleteCreditCardRequest, CreditCard.class);

		this.creditCardDao.delete(creditCard);

		return new SuccessResult(BusinessMessages.CREDIT_CARD_DELETED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CreditCardListDto>> getAll() {

		List<CreditCard> result = this.creditCardDao.findAll();
		List<CreditCardListDto> response = result.stream()
				.map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CreditCardListDto>>(response,
				BusinessMessages.CREDIT_CARDS_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<CreditCardDto> getById(int creditCardId) {

		CreditCard result = this.creditCardDao.getById(creditCardId);
		CreditCardDto response = this.modelMapperService.forDto().map(result, CreditCardDto.class);

		return new SuccessDataResult<CreditCardDto>(response, 
				BusinessMessages.CREDIT_CARD_LISTED_SUCCESSFULLY);
	}
}
