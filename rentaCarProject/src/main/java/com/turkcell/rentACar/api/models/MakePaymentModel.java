package com.turkcell.rentACar.api.models;

import java.util.List;

import com.sun.istack.NotNull;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.creates.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.creates.CreatePaymentRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakePaymentModel {

	@NotNull
	private CreatePaymentRequest createPaymentRequest;

	@NotNull
	private CreateCarRentalRequest createCarRentalRequest;

	@NotNull
	private CreateInvoiceRequest createInvoiceRequest;

    private List<CreateOrderedAdditionalServiceRequest>
	         createOrderedAdditionalServiceRequests;

}
