package com.demo.restaurant.dto;

public class TableDto {

	private Long id;

	private Integer number;

	private Integer maxNumberOfPersons;

	private Long bookId;


	
	public TableDto(Long id, Integer number, Integer maxNumberOfPersons, Long bookId) {
		super();
		this.id = id;
		this.number = number;
		this.maxNumberOfPersons = maxNumberOfPersons;
		this.bookId = bookId;
	}


	public TableDto(Long id, Integer number, Integer maxNumberOfPersons) {
		super();
		this.id = id;
		this.number = number;
		this.maxNumberOfPersons = maxNumberOfPersons;
	}


	public Long getBookId() {
		return bookId;
	}


	public void setBookId(Long bookId) {
		this.bookId = bookId;
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


	@Override
	public String toString() {
		return "TableDto [id=" + id + ", number=" + number + ", maxNumberOfPersons=" + maxNumberOfPersons + ", bookId="
				+ bookId + "]";
	}

}
