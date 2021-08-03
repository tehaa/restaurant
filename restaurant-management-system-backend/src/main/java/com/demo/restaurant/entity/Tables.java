package com.demo.restaurant.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tables")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tables {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "number")
	private Integer number;

	@Column(name = "max_number_of_persons")
	private Integer maxNumberOfPersons;

	@OneToMany(mappedBy = "tables", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Booking> bookings;

	public Tables() {
		super();
	}

	public Tables(Long id, Integer number, Integer maxNumberOfPersons) {
		super();
		this.id = id;
		this.number = number;
		this.maxNumberOfPersons = maxNumberOfPersons;
	}

	public Tables(Integer number, Integer maxNumberOfPersons) {
		super();
		this.number = number;
		this.maxNumberOfPersons = maxNumberOfPersons;
	}

	public Tables(Integer maxNumberOfPersons) {
		super();
		this.maxNumberOfPersons = maxNumberOfPersons;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getMaxNumberOfPersons() {
		return maxNumberOfPersons;
	}

	public void setMaxNumberOfPersons(Integer maxNumberOfPersons) {
		this.maxNumberOfPersons = maxNumberOfPersons;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	@Override
	public String toString() {
		return "RestaurantTable [id=" + id + ", number=" + number + ", maxNumberOfPersons=" + maxNumberOfPersons + "]";
	}

}
