package com.turkcell.rentACar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rental")
public class Rental {

	@Id
	@Column(name = "rental_id")
	private String rentalId;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "return_date")
	private LocalDate returnDate;
	
	@Column(name = "late_return_date")
	private LocalDate lateReturnDate;
	
	@Column(name = "rent_status")
	private boolean rentStatus = true;

	@Column(name = "starting_kilometer")
	private String startingKilometer;

	@Column(name = "finish_kilometer")
	private String finishKilometer;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;
	
	@ManyToOne
	@JoinColumn(name="rental_city_id")
	private City rentalCity;
	
	@ManyToOne
	@JoinColumn(name="return_city_id")
	private City returnCity;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "rental")
	private List<OrderedAdditionalService> 
	      orderedAdditionalService;

	@OneToMany(mappedBy="rental")
	private List<Payment> payments;
	
	@OneToMany(mappedBy="rental")
	private List<Invoice> invoices;

}
