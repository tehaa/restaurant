package com.demo.restaurant.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "booking")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "book_date")
	private Long bookDate;

	@Column(name = "number_of_persons")
	private Integer numberOfPersons;

	@Column(name = "insert_date")
	private Date insertDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "table_id")
	private Tables tables;

	public Booking() {
		super();
	}

	public Booking(Long id) {
		super();
		this.id = id;
	}



	public Booking(Long bookDate, Integer numberOfPersons) {
		super();
		this.bookDate = bookDate;
		this.numberOfPersons = numberOfPersons;
		this.insertDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBookDate() {
		return bookDate;
	}

	public void setBookDate(Long bookDate) {
		this.bookDate = bookDate;
	}

	public Integer getNumberOfPersons() {
		return numberOfPersons;
	}

	public void setNumberOfPersons(Integer numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tables getTables() {
		return tables;
	}

	public void setTables(Tables tables) {
		this.tables = tables;
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", bookDate=" + bookDate + ", numberOfPersons=" + numberOfPersons + ", insertDate="
				+ insertDate + "]";
	}

}
