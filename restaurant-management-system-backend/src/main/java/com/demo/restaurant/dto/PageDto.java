package com.demo.restaurant.dto;

public class PageDto {

	private int pageSize;

	private long totalElements;

	private int pageIndex;

	public PageDto() {
		super();
	}

	public PageDto(int pageSize, long totalElements, int pageIndex) {
		super();
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}
