package com.demo.restaurant.dto;

import java.util.List;

public class BookingPageDto extends PageDto {

	private List<BookingDto> bookList;

	private String responseDescrption;

	public BookingPageDto() {
		super();
	}

	public BookingPageDto(int pageSize, long totalElements, int pageIndex) {
		super(pageSize, totalElements, pageIndex);
	}
	
	



	public BookingPageDto(int pageSize, long totalElements, int pageIndex, List<BookingDto> bookList,
			String responseDescrption) {
		super(pageSize, totalElements, pageIndex);
		this.bookList = bookList;
		this.responseDescrption = responseDescrption;
	}

	public List<BookingDto> getBookList() {
		return bookList;
	}

	public void setBookList(List<BookingDto> bookList) {
		this.bookList = bookList;
	}

	public String getResponseDescrption() {
		return responseDescrption;
	}

	public void setResponseDescrption(String responseDescrption) {
		this.responseDescrption = responseDescrption;
	}

}
