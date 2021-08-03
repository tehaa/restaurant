package com.demo.restaurant.service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.catalina.connector.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.restaurant.controller.TableController;
import com.demo.restaurant.dto.TableDto;
import com.demo.restaurant.entity.Booking;
import com.demo.restaurant.entity.Tables;
import com.demo.restaurant.repository.TablesRepository;
import com.demo.restaurant.util.ResponseMessage;

@Service
public class TablesService {

	@Autowired
	private TablesRepository tablesRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(TableController.class);

	/**
	 * 
	 * @param numberOfPersons
	 * @param bookDate
	 * @param paging
	 * @return
	 */
	public ResponseEntity<?> getAvaiableTables(int numberOfPersons, Date bookingDate, int page, int size) {

		LOGGER.debug("----->start getting page : {} size : {} for not booked tables in date : {} ", page, size,
				bookingDate);

		Pageable paging = PageRequest.of(page, size);
		try {
			Page<TableDto> tablesPage = tablesRepository.getAvaiableTables(numberOfPersons, bookingDate.getTime(),
					paging);

			if (tablesPage.isEmpty()) {
				LOGGER.debug("-----> no tables for page: {}  are avaiable in this date: {} ", page, bookingDate);
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body(new ResponseMessage(ResponseMessage.NO_AVAIABLE_TABLES));
			} else {
				LOGGER.debug("----->successfully return page : {} of avaiable tables in this date :{}", page,
						bookingDate);
				return ResponseEntity.status(HttpStatus.OK).body(tablesPage);
			}
		} catch (Exception e) {
			LOGGER.error("----->error while getting page: {} in date : {} for avaiable tables", page, bookingDate, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage(ResponseMessage.ERROR_WHILE_GEETING_DATA));

		}
	}

	/**
	 * 
	 * @param tables
	 * @return
	 */
	public ResponseEntity<ResponseMessage> addTable(Tables tables) {
		try {
			if (Objects.nonNull(tables.getNumber())) {

				LOGGER.debug("----->start adding table number : {} to the tables in the system", tables.getNumber());
				if (Objects.isNull(tablesRepository.findByNumber(tables.getNumber()))) {

					LOGGER.debug("----->table number : {} never added before and start saving it ", tables.getNumber());
					tablesRepository.save(tables);
					LOGGER.debug("----->adding table number :{} successfully ", tables.getNumber());
					return ResponseEntity.status(HttpStatus.OK)
							.body(new ResponseMessage(ResponseMessage.TABLE_CREATED_SUCCESSFULLY));
				} else
					LOGGER.error("----->table number: {} was addedbefore chose another number ", tables.getNumber());

				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseMessage(ResponseMessage.TABLE_NUMBER_WAS_ADDED_BEFORE));
			} else {
				LOGGER.error("----->please add table number to the table");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseMessage(ResponseMessage.ERROR_ON_PARAMS));
			}
		} catch (Exception e) {
			LOGGER.error("----->error while adding table to the database", e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage(ResponseMessage.ERROR_WHILE_ADDING_DATA));
		}
	}

	/**
	 * 
	 * @param paging
	 * @return
	 */
	public ResponseEntity<?> getTables(int page, int size) {
		try {
			Pageable paging = PageRequest.of(page, size);

			LOGGER.debug("----->start geeting page: {} size:{} of tables in the system ", page, size);

			Page<Tables> tablesPage = tablesRepository.findAll(paging);

			if (tablesPage.isEmpty()) {
				LOGGER.debug("----->no data avaiable for page :{} in the tables", page);
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body(new ResponseMessage(ResponseMessage.EMPTY_TABLE_PAGE));

			} else {
				LOGGER.debug("----->successfully geting page : {} of tables", page);
				return ResponseEntity.status(HttpStatus.OK).body(tablesPage);

			}
		} catch (Exception e) {
			LOGGER.error("----->error while adding tables", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ResponseMessage.ERROR_WHILE_GEETING_DATA);

		}
	}

	/**
	 * 
	 * @param tableId
	 * @return
	 */
	public ResponseEntity<ResponseMessage> deleteTable(Long tableId) {
		try {
			LOGGER.debug("------>start service delete table to delete table id:{}", tableId);

			Optional<Tables> tableOptional = tablesRepository.findById(tableId);
			if (tableOptional.isPresent()) {
				
				//check if table have bookings or not
				if(Objects.isNull(tableOptional.get().getBookings())) {
				
				tablesRepository.deleteById(tableId);
				LOGGER.debug("------>successfully delete table id:{}", tableId);

				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(ResponseMessage.SUCCESSFULLY_DELETED));
				}else {
					LOGGER.error("----->cant able to delete table: {} that have bookings Delete booking and then delete table",tableId);
					
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ResponseMessage(ResponseMessage.TABLE_HAS_BOOKINGS));
				}
			} else {
				LOGGER.error("----->No tables found in database for id : {}", tableId);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseMessage(ResponseMessage.NOT_VALID_TABLE));

			}
		} catch (Exception e) {
			LOGGER.error("----->error while deleteing tables from system", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage(ResponseMessage.ERROR_WHILE_DELETING_TABLE));

		}
	}

}
