package com.demo.restaurant;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.demo.restaurant.controller.BookingController;
import com.demo.restaurant.dto.BookingDto;
import com.demo.restaurant.entity.Booking;
import com.demo.restaurant.entity.Tables;
import com.demo.restaurant.entity.User;
import com.demo.restaurant.repository.BookingRepository;
import com.demo.restaurant.repository.TablesRepository;
import com.demo.restaurant.repository.UserRepository;
import com.demo.restaurant.service.BookingService;
import com.demo.restaurant.util.ResponseMessage;

class BookingControllerTest {

	@Autowired
	BookingController BookingController;

	@InjectMocks
	BookingService BookingService;

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private TablesRepository tablesRepository;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	private void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void testGetBookings() {
		Pageable paging = PageRequest.of(0, 2);

		List<BookingDto> bokBookingDtos = new ArrayList<BookingDto>();

		BookingDto bookingDto1 = new BookingDto((long) 1, "ahmed", new Date(), 5, 5);
		BookingDto bookingDto2 = new BookingDto((long) 2, "ali", new Date(), 5, 5);

		bokBookingDtos.add(bookingDto2);
		bokBookingDtos.add(bookingDto1);

		Page<BookingDto> bookingDtoPage = new PageImpl<>(bokBookingDtos);

		Page<BookingDto> emptyPage = new PageImpl<>(new ArrayList<BookingDto>());

		when(tablesRepository.getBookingTable(new Date("2020/04/19 12:00").getTime(), paging))
				.thenReturn(bookingDtoPage);

		when(tablesRepository.getBookingTable(new Date("2021/04/19 12:00").getTime(), paging)).thenReturn(emptyPage);

		assertAll(
				() -> assertEquals(bookingDtoPage.getContent().size(),
						BookingService.getBookings(new Date("2020/04/19 12:00"), 0, 2).getBody().getBookList().size()),
				() -> assertEquals(ResponseMessage.EMPTY_BOOKING, BookingService
						.getBookings(new Date("2021/04/19 12:00"), 0, 2).getBody().getResponseDescrption()));
		
	} 
	
	@Test
	void testBookingTable() {
		User user = new User("ahmed", "ahmed", "123456");

		Tables tables = new Tables((long) 1, 5, 5);

		BookingDto invlidBookingParams = new BookingDto(null, (long) 3, new Date(), 1, 1);

		BookingDto invlidBookingUserName = new BookingDto("yyyyyy", (long) 3, new Date(), 1, 1);

		BookingDto InvalidBookingTable = new BookingDto("ahmed", (long) 0, new Date(), 1, 1);

		BookingDto validBookingRequest = new BookingDto("ahmed", (long) 3, new Date(), 1, 1);

		when(userRepository.findByUsername(invlidBookingUserName.getUsername())).thenReturn(null);

		when(userRepository.findByUsername(InvalidBookingTable.getUsername())).thenReturn(user);

		when(tablesRepository.findById(InvalidBookingTable.getTableId())).thenReturn(Optional.empty());

		when(tablesRepository.findById(validBookingRequest.getTableId())).thenReturn(Optional.of(tables));

		when(userRepository.findByUsername(validBookingRequest.getUsername())).thenReturn(user);

		assertAll(
				() -> assertEquals(ResponseMessage.ERROR_ON_PARAMS,
						BookingService.bookTable(invlidBookingParams).getBody().getDescription()),
				() -> assertEquals(ResponseMessage.NOT_VALID_USERNAME,
						BookingService.bookTable(invlidBookingUserName).getBody().getDescription()),
				() -> assertEquals(ResponseMessage.NOT_VALID_TABLE,
						BookingService.bookTable(InvalidBookingTable).getBody().getDescription()),
				() -> assertEquals(ResponseMessage.SUCCESSFULLY_BOOKING,
						BookingService.bookTable(validBookingRequest).getBody().getDescription())

		);

	}
	
	@Test
	void testDeleteBooking() {

		Booking booking = new Booking((long) 40);

		when(bookingRepository.findById((long) 0)).thenReturn(Optional.empty());
		when(bookingRepository.findById((long) 40)).thenReturn(Optional.of(booking));

		assertAll(
				() -> assertEquals(ResponseMessage.SUCCESSFULLY_DELETED,
						BookingService.deleteBooking(booking.getId()).getBody().getDescription()),
				() -> assertEquals(ResponseMessage.BOOKING_ID_NOT_VALID,
						BookingService.deleteBooking((long) 0).getBody().getDescription())

		);

	}


}
