package com.demo.restaurant.controller;

import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.restaurant.dto.BookingDto;
import com.demo.restaurant.dto.BookingPageDto;
import com.demo.restaurant.service.BookingService;
import com.demo.restaurant.util.ResponseMessage;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	private BookingService bookingService;

	@ApiOperation(value = "This Api is responsible for geeting all bookings that booked in given date")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookingPageDto> getBookingTables(
			@RequestParam(required = false, defaultValue = "0", name = "pageIndex") int page,
			@RequestParam(required = false, defaultValue = "3", name = "pageSize") int size,
			@RequestParam(required = true, name = "bookingDate") Date bookingDate) {
		LOGGER.debug("----->start /api get booking tables");
		
		return bookingService.getBookings(bookingDate, page, size);

	}

	@ApiOperation(value = "This Api is responsible add a new booking to table in the system  ")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> bookingTable(@RequestBody BookingDto bookDto) {

		LOGGER.debug("----->start /api post booking table  ");
		return bookingService.bookTable(bookDto);

	}

	@ApiOperation(value = "This Api will delete  Booking order from the system ")
	@RequestMapping(value = "{bookingId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> deleteBooking(@PathVariable Long bookingId) {
		LOGGER.debug("----->start /api delete booking");
		
		return bookingService.deleteBooking(bookingId);
	}

}
