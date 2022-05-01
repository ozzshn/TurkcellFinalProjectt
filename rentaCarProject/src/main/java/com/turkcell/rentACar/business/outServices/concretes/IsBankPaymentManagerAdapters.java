package com.turkcell.rentACar.business.outServices.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.turkcell.rentACar.business.outServices.abstracts.IsBankPaymentService;
import com.turkcell.rentACar.core.service.abstracts.FakeBankPaymentServiceAdapter;
import com.turkcell.rentACar.entities.concretes.Payment;

@Component
@Qualifier("isbank")
public class IsBankPaymentManagerAdapters implements FakeBankPaymentServiceAdapter {

	private IsBankPaymentService isBankPaymentService;
	
	@Autowired
	public IsBankPaymentManagerAdapters(IsBankPaymentService isBankPaymentService) {
		this.isBankPaymentService = isBankPaymentService;
	}
	
	@Override
	public boolean payment(Payment payment) {
		return this.isBankPaymentService.payment(payment.getExpiryDate(),
				payment.getCvvNo(), payment.getCreditCardNo(), payment.getCardHolder());
	}

}
