package com.demo.restaurant.util;

public class ResponseMessage {

	public static final String NOT_VALID_USERNAME = "enter valid Email";

	public static final String NOT_VALID_NAME = "enter valid name";

	public static final String NOT_VALID_PHONE = "enter valid phone";

	public static final String NOT_VALID_PASSWORD = "enter valid password";

	public static final String DUBLICATE_USERNAME = "email was registered before";

	public static final String USER_LOGIN_SUCCESSFULLY = "user login successfully";

	public static final String USER_REGISTERED_SUCCESSFULLY = "user registered successfully press yes to go to login page";

	public static final String TABLE_CREATED_SUCCESSFULLY = "Table created successfully";

	public static final String ERROR_WHILE_GEETING_DATA = "Error while geeting data from database";

	public static final String ERROR_WHILE_ADDING_DATA = "Error while adding data to database";

	public static final String EMPTY_BOOKING = "No Booking was Booked in this day";

	public static final String EMPTY_TABLE_PAGE = "No tables For this page";

	public static final String ERROR_ON_PARAMS = "error on paramters";

	public static final String SUCCESSFULLY_DELETED = "Deleted Successfully";

	public static final String ERROR_WHILE_BOOKING = "error while booking";

	public static final String NOT_VALID_TABLE = "enter valid table Id";

	public static final String SUCCESSFULLY_BOOKING = "Booking Done Successfully";

	public static final String BOOKING_ID_NOT_VALID = "Booking id is not valid";

	public static final String ERROR_WHILE_DELETING_BOOKING = "Error while deleting booking";

	public static final String ERROR_WHILE_DELETING_TABLE = "Error while deleting table";

	public static final String GETTING_BOOKING_PAGE_SUCCESSFULLY = "getting booking page successfully";

	public static final String TABLE_HAS_BOOKINGS = "This table has Bookings remove bookings and then remove tables";

	public static final String NO_AVAIABLE_TABLES = "no tables avaiable in this date";

	public static final String TABLE_NUMBER_WAS_ADDED_BEFORE = "Error while adding  table !  number was added before to Existing table";
	private String description;

	public ResponseMessage(String description) {
		super();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
