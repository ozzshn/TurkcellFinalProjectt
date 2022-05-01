package com.turkcell.rentACar.core.service.concretes;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.turkcell.rentACar.core.service.abstracts.FakeBankPaymentServiceAdapter;
import com.turkcell.rentACar.entities.concretes.Payment;

@Component
@Qualifier("fakeBankPayment")
public class FakeBankPaymentManager implements FakeBankPaymentServiceAdapter {

	@Override
	public boolean payment(Payment payment) {
		return true;
	}
}
