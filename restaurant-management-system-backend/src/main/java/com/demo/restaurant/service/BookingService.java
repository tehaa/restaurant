package com.demo.restaurant.service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.restaurant.dto.BookingDto;
import com.demo.restaurant.dto.BookingPageDto;
import com.demo.restaurant.entity.Booking;
import com.demo.restaurant.entity.Tables;
import com.demo.restaurant.entity.User;
import com.demo.restaurant.repository.BookingRepository;
import com.demo.restaurant.repository.TablesRepository;
import com.demo.restaurant.repository.UserRepository;
import com.demo.restaurant.util.ResponseMessage;

@Service
public class BookingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private TablesRepository tablesRepository;

	/**
	 * 
	 * @param bookDto
	 * @return
	 */
	public ResponseEntity<ResponseMessage> bookTable(BookingDto bookDto) {

		try {
			if (Objects.nonNull(bookDto.getUsername()) && Objects.nonNull(bookDto.getTableId())
					&& Objects.nonNull(bookDto.getBookDate()) && Objects.nonNull(bookDto.getNumberOfPersons())) {

				Booking booking = new Booking(bookDto.getBookDate().getTime(), bookDto.getNumberOfPersons());

				LOGGER.debug("----->start booking table :{} to username: {}", bookDto.getTableId(),
						bookDto.getUsername());

				User user = userRepository.findByUsername(bookDto.getUsername());

				if (Objects.nonNull(user)) {

					booking.setUser(user);
					LOGGER.debug("----->successfully getting user : {} and add booking to user", user.getUsername());
				} else {
					LOGGER.error("----->invalid username : {} this user isn't on the system", bookDto.getUsername());
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ResponseMessage(ResponseMessage.NOT_VALID_USERNAME));
				}

				Optional<Tables> tables = tablesRepository.findById(bookDto.getTableId());

				if (tables.isPresent()) {
					booking.setTables(tables.get());
					LOGGER.debug("----->successfully getting table : {} and add booking to table",
							bookDto.getTableId());

				} else {
					LOGGER.error("----->invalid TABLE : {} this user isn't on the system", bookDto.getTableId());
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ResponseMessage(ResponseMessage.NOT_VALID_TABLE));

				}

				booking.setBookDate(bookDto.getBookDate().getTime());
				booking.setNumberOfPersons(bookDto.getNumberOfPersons());

				booking.setInsertDate(new Date());

				bookingRepository.save(booking);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new ResponseMessage(ResponseMessage.SUCCESSFULLY_BOOKING));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseMessage(ResponseMessage.ERROR_ON_PARAMS));
			}
		} catch (Exception e) {
			LOGGER.error("error while booking :{} ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage(ResponseMessage.ERROR_WHILE_BOOKING));

		}
	}

	/**
	 * method that return page of bookings in specific date using left join between
	 * tables and bookings booking table and return tables that has book id not null
	 * in this date
	 * 
	 * @param bookDate
	 * @param page
	 * @param size
	 * @return
	 */

	public ResponseEntity<BookingPageDto> getBookings(Date bookingDate, int page, int size) {

		LOGGER.debug("----->start service get Bookings to get bookings table page :{} which size :{} in date: {}", page,
				size, bookingDate);
		BookingPageDto bookingPageDto;

		Pageable paging = PageRequest.of(page, size);

		try {
			Page<BookingDto> bookingDtoPage = tablesRepository.getBookingTable(bookingDate.getTime(), paging);
			if (bookingDtoPage.isEmpty()) {
				LOGGER.debug("----->No Bookings for page : {} in this date : {}", page, bookingDate);

				bookingPageDto = new BookingPageDto();

				bookingPageDto.setResponseDescrption(ResponseMessage.EMPTY_BOOKING);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(bookingPageDto);

			} else {

				LOGGER.debug("----->successfully geting page : {} that booked in date: {} ", page, bookingDate);
				bookingPageDto = new BookingPageDto(bookingDtoPage.getSize(), bookingDtoPage.getTotalElements(),
						bookingDtoPage.getNumber(), bookingDtoPage.getContent(),
						ResponseMessage.GETTING_BOOKING_PAGE_SUCCESSFULLY);

				return ResponseEntity.status(HttpStatus.OK).body(bookingPageDto);

			}

		} catch (Exception e) {
			LOGGER.error("----->error while geting booking page: {} ", e);

			bookingPageDto = new BookingPageDto();

			bookingPageDto.setResponseDescrption(ResponseMessage.ERROR_WHILE_GEETING_DATA);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bookingPageDto);

		}

	}

	/**
	 * 
	 * @param bookId
	 * @return
	 */

	public ResponseEntity<ResponseMessage> deleteBooking(Long bookingId) {
		try {
			LOGGER.debug("------>start service delete booking to delete booking id:{}", bookingId);

			Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
			if (optionalBooking.isPresent()) {
				bookingRepository.deleteById(bookingId);
				LOGGER.debug("------>successfully delete booking id:{}", bookingId);

				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(ResponseMessage.SUCCESSFULLY_DELETED));
			} else {
				LOGGER.error("No booking found in database for id : {}", bookingId);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseMessage(ResponseMessage.BOOKING_ID_NOT_VALID));

			}
		} catch (Exception e) {
			LOGGER.error("----->error while deleteing booking from system", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage(ResponseMessage.ERROR_WHILE_DELETING_BOOKING));

		}

	}

}
