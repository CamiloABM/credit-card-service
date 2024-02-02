package com.bank.card.exception;

import lombok.Data;

@Data
public class ErrorResponse {

	private int code;
	private String message;
	private String detail;

	public ErrorResponse(int code, String message, String detail) {
		this.code = code;
		this.message = message;
		this.detail = detail;
	}
}
