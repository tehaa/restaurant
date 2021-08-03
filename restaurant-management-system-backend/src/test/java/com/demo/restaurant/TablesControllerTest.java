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
import org.springframework.http.HttpStatus;

import com.demo.restaurant.controller.TableController;
import com.demo.restaurant.dto.TableDto;
import com.demo.restaurant.entity.Booking;
import com.demo.restaurant.entity.Tables;
import com.demo.restaurant.repository.TablesRepository;
import com.demo.restaurant.service.TablesService;
import com.demo.restaurant.util.ResponseMessage;

public class TablesControllerTest {

	@Autowired
	TableController tableController;

	@InjectMocks
	TablesService tablesService;

	@Mock
	private TablesRepository tablesRepository;

	@BeforeEach
	private void init() {
		MockitoAnnotations.initMocks(this);
		Tables tables = new Tables(10, 10);

		Tables tables2 = new Tables(5, 5);

		Tables tables3 = new Tables(5);
	}

	@Test
	void testGetAvaiableTables() {
		List<TableDto> tableDtos = new ArrayList<TableDto>();
		Pageable paging = PageRequest.of(0, 2);

		TableDto tableDto = new TableDto((long) 1, 5, 5);
		TableDto tableDto2 = new TableDto((long) 1, 5, 5);

		tableDtos.add(tableDto);
		tableDtos.add(tableDto2);

		Page<TableDto> tablePageDto = new PageImpl<>(tableDtos);

		Page<TableDto> emptyPage = new PageImpl<>(new ArrayList<TableDto>());

		when(tablesRepository.getAvaiableTables(5, new Date("2020/04/19 12:00").getTime(), paging))
				.thenReturn(tablePageDto);
		when(tablesRepository.getAvaiableTables(5, new Date("2021/04/18 12:00").getTime(), paging))
				.thenReturn(emptyPage);

		assertAll(
				() -> assertEquals(HttpStatus.OK,
						tablesService.getAvaiableTables(5, new Date("2020/04/19 12:00"), 0, 2).getStatusCode()),
				() -> assertEquals(HttpStatus.NO_CONTENT,
						tablesService.getAvaiableTables(5, new Date("2021/04/18 12:00"), 0, 2).getStatusCode())

		);
	}

	@Test
	void testAddTable() {
		Tables tables = new Tables(10, 10);

		Tables tables2 = new Tables(5, 5);

		Tables tables3 = new Tables(5);

		when(tablesRepository.findByNumber(5)).thenReturn(null);

		when(tablesRepository.findByNumber(10)).thenReturn(tables);

		assertAll(
				() -> assertEquals(ResponseMessage.TABLE_CREATED_SUCCESSFULLY,
						tablesService.addTable(tables2).getBody().getDescription()),
				() -> assertEquals(ResponseMessage.TABLE_NUMBER_WAS_ADDED_BEFORE,
						tablesService.addTable(tables).getBody().getDescription()),
				() -> assertEquals(ResponseMessage.ERROR_ON_PARAMS,
						tablesService.addTable(tables3).getBody().getDescription()));

	}

	@Test
	void testGetTables() {

		Pageable avaiablePage = PageRequest.of(0, 2);
		Pageable notAvaiablePage = PageRequest.of(10, 2);

		Tables tables = new Tables(10, 10);

		Tables tables2 = new Tables(5, 5);

		List<Tables> tabList = new ArrayList<Tables>();
		tabList.add(tables);
		tabList.add(tables2);

		Page<Tables> emptyPage = new PageImpl<>(new ArrayList<Tables>());
		Page<Tables> NonEmptyPage = new PageImpl<>(tabList);

		when(tablesRepository.findAll(avaiablePage)).thenReturn(NonEmptyPage);
		when(tablesRepository.findAll(notAvaiablePage)).thenReturn(emptyPage);

		assertAll(() -> assertEquals(HttpStatus.OK, tablesService.getTables(0, 2).getStatusCode()),
				() -> assertEquals(HttpStatus.NO_CONTENT, tablesService.getTables(10, 2).getStatusCode())

		);

	}

	@Test	
	void testDeleteTable() {

		Tables tables = new Tables((long) 5, 5, 5);

		when(tablesRepository.findById((long) 0)).thenReturn(Optional.empty());
		when(tablesRepository.findById(tables.getId())).thenReturn(Optional.of(tables));

		assertAll(
				() -> assertEquals(ResponseMessage.SUCCESSFULLY_DELETED,
						tablesService.deleteTable(tables.getId()).getBody().getDescription()),
				() -> assertEquals(ResponseMessage.NOT_VALID_TABLE,
						tablesService.deleteTable((long) 0).getBody().getDescription())

		);

	}

}
