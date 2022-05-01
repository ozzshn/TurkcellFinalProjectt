package com.turkcell.rentACar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_cards")
@Entity
public class CreditCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "credit_card_id")
	private int creditCardId;
	
	@Column(name = "credit_card_no")
	private String creditCardNo;
	
	@Column(name="expiry_date")
	private LocalDate expiryDate;

	@Column(name = "cvv_no")
	private String cvvNo;

	@Column(name = "card_holder")
	private String cardHolder;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
