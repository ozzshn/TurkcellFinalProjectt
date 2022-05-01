package com.turkcell.rentACar.business.outServices.concretes;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.outServices.abstracts.IsBankPaymentService;

@Service
public class IsBankPaymentManager implements IsBankPaymentService{

	@Override
	public boolean payment(LocalDate expiryDate, String cvvNo, String creditCardNo, String cardHolder) {
		return true;
	}

}
