package com.demo.restaurant.dto;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDto {

	private Long bookId;

	private String username;

	private Long tableId;

	private Date bookDate;

	private Integer numberOfPersons;

	private Integer tableNumber;

	public BookingDto(Long bookId, String username, Date bookDate, Integer numberOfPersons, Integer tableNumber) {
		super();
		this.bookId = bookId;
		this.username = username;
		this.bookDate = bookDate;
		this.numberOfPersons = numberOfPersons;
		this.tableNumber = tableNumber;
	}

	public BookingDto(String username, Long tableId, Date bookDate, Integer numberOfPersons, Integer tableNumber) {
		super();
		this.username = username;
		this.tableId = tableId;
		this.bookDate = bookDate;
		this.numberOfPersons = numberOfPersons;
		this.tableNumber = tableNumber;
	}

	public BookingDto(Long bookId, String username, Long tableId, Date bookDate, Integer numberOfPersons,
			Integer tableNumber) {
		super();
		this.bookId = bookId;
		this.username = username;
		this.tableId = tableId;
		this.bookDate = bookDate;
		this.numberOfPersons = numberOfPersons;
		this.tableNumber = tableNumber;
	}

	public BookingDto() {
		super();
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}

	public Integer getNumberOfPersons() {
		return numberOfPersons;
	}

	public void setNumberOfPersons(Integer numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}

	@Override
	public String toString() {
		return "BookingDto [bookId=" + bookId + ", username=" + username + ", tableId=" + tableId + ", bookDate="
				+ bookDate + ", numberOfPersons=" + numberOfPersons + ", tableNumber=" + tableNumber + "]";
	}

}
