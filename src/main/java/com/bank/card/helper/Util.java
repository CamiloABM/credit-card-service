package com.bank.card.helper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Util {

	@Value("${service.yearExpiration}")
	private int yearExpiration;

	public String generateCardNumber() {
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");
		return localDate.format(formatter);
	}

	/* Usar Calendar mejor ?¿ */

	public int getExpirationMonth() {
		LocalDate currentDate = LocalDate.now();
		LocalDate currentDatePlus3 = currentDate.plusYears(yearExpiration);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
		return Integer.valueOf(currentDatePlus3.format(formatter));
	}

	public int getExpirationYear() {
		LocalDate currentDate = LocalDate.now();
		LocalDate currentDatePlus3 = currentDate.plusYears(yearExpiration);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
		return Integer.valueOf(currentDatePlus3.format(formatter));
	}

	public String generateTransacionId() {
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		return localDate.format(formatter);
	}

	public Timestamp getCurrentDate() {
		LocalDateTime fechaActual = LocalDateTime.now();
		return Timestamp.valueOf(fechaActual);
	}

}
