package com.turkcell.rentACar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "payment_id")
	private int paymentId;

	@Column(name = "credit_card_no")
	private String creditCardNo;

	@Column(name = "card_holder")
	private String cardHolder;

	@Column(name = "expiry_date")
	private LocalDate expiryDate;

	@Column(name = "cvv_no")
	private String cvvNo;

	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="rental_id")
	private Rental rental;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "payment")
	private List<Invoice> invoices;

}
