package com.demo.restaurant.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.restaurant.dto.BookingDto;
import com.demo.restaurant.dto.TableDto;
import com.demo.restaurant.entity.Tables;

@Repository
public interface TablesRepository extends JpaRepository<Tables, Long> {

	@Query(value = "SELECT  new com.demo.restaurant.dto.TableDto(tab.id as id ,tab.number as number,tab.maxNumberOfPersons as maxNumberOfPersons ,bo.id as bookId)"
			+ " FROM Tables as tab  LEFT JOIN Booking as bo On (tab.id=bo.tables.id) AND (bo.bookDate = :bookingDate) where tab.maxNumberOfPersons >= :numberOfPersons  And bo.id IS null")
	Page<TableDto> getAvaiableTables(@Param("numberOfPersons") Integer numberOfPersons,
			@Param("bookingDate") Long bookingDate, Pageable pageable);

	Tables findByNumber(Integer number);

	@Query(value = "SELECT new com.demo.restaurant.dto.BookingDto(bo.id as bookId,user.username as username,tab.id as tableId ,bo.insertDate as bookDate,bo.numberOfPersons as numberOfPersons"
			+ ",tab.number as tableNumber)  FROM Tables as tab  LEFT JOIN Booking as bo On (tab.id=bo.tables.id) AND (bo.bookDate = :bookingDate)  LEFT  JOIN User AS user ON (bo.user.id=user.id) where bo.id IS NOT null")
	Page<BookingDto> getBookingTable(@Param("bookingDate") Long bookingDate, Pageable paging);

}
