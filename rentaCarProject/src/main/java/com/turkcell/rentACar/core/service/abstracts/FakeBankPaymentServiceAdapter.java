package com.turkcell.rentACar.core.service.abstracts;


import org.springframework.stereotype.Service;


import com.turkcell.rentACar.entities.concretes.Payment;

@Service
public interface FakeBankPaymentServiceAdapter {

	public boolean payment(Payment payment);


}
