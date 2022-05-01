package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.api.models.MakePaymentModel;
import com.turkcell.rentACar.business.dtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.deletes.DeletePaymentRequest;
import com.turkcell.rentACar.business.requests.updates.UpdatePaymentRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface PaymentService {

	Result addPaymentForIndividualCustomer(MakePaymentModel makePaymentModel) throws BusinessException;

	Result addPaymentForCorporateCustomer(MakePaymentModel makePaymentModel) throws BusinessException;

	Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException;

	Result update(UpdatePaymentRequest updatePaymentRequest) throws BusinessException;

	DataResult<PaymentDto> getById(int paymentId) throws BusinessException;

	DataResult<List<PaymentListDto>> getAll();

}
