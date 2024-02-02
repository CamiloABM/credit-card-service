package com.bank.card.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ErrorResponse {

	@Schema(description = "Codigo error")
	private int code;
	@Schema(description = "Mensaje error")
	private String message;
	@Schema(description = "Detalle error")
	private String detail;

	public ErrorResponse(int code, String message, String detail) {
		this.code = code;
		this.message = message;
		this.detail = detail;
	}
}
