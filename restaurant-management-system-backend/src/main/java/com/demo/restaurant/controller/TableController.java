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

import com.demo.restaurant.entity.Tables;
import com.demo.restaurant.service.TablesService;
import com.demo.restaurant.util.ResponseMessage;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/tables")
public class TableController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TableController.class);

	@Autowired
	private TablesService tableService;

	@ApiOperation(value = "This Api will get all available tables that not booked in the given date ")
	@RequestMapping(value = "avaiableTables", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<?> getTables(
			@RequestParam(required = false, defaultValue = "0", name = "pageIndex") int page,
			@RequestParam(required = false, defaultValue = "3", name = "pageSize") int size,
			@RequestParam(required = true, name = "numberOfPersons") int numberOfPersons,
			@RequestParam(required = true, name = "bookingDate") Date bookingDate) {

		LOGGER.debug("----->start /api get avaiable tables");
		return tableService.getAvaiableTables(numberOfPersons, bookingDate, page, size);
	}

	@ApiOperation(value = "This Api will add a new table to the restaurant in the system ")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ResponseMessage> addTable(@Valid @RequestBody Tables tables) {
		LOGGER.debug("----->start /api post table");
		return tableService.addTable(tables);

	}

	@ApiOperation(value = "This Api will get all tables in the syatem  ")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<?> getTables(
			@RequestParam(required = false, defaultValue = "0", name = "pageIndex") int page,
			@RequestParam(required = false, defaultValue = "3", name = "pageSize") int size) {
		LOGGER.debug("----->start /api get tables");
		
		return tableService.getTables(page,size);

	}

	@ApiOperation(value = "This Api will delete  table  from the system ")
	@RequestMapping(value = "{tableId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ResponseMessage> deletetable(@PathVariable Long tableId) {
		LOGGER.debug("----->start /api delete tables");
		return tableService.deleteTable(tableId);
	}

}
