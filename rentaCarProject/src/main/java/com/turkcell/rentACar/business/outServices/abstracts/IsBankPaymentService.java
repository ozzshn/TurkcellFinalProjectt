package com.turkcell.rentACar.business.outServices.abstracts;

import java.time.LocalDate;

public interface IsBankPaymentService {

	public boolean payment(LocalDate expiryDate, String cvvNo, String creditCardNo, String cardHolder);

}
