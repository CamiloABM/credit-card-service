package com.bank.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TransactionDTO {

	/* Número Tarjeta */
	@NotNull(message = "El número de tarjeta es obligatorio")
	@NotBlank(message = "El número de tarjeta es obligatorio")
	@Pattern(regexp = "\\d{16}", message = "El número de tarjeta debe contener 16 dígitos numericos.")
	@Schema(description = "Número de tarjeta (16 digitos númericos)")
	private String cardId;

	@Min(value = 0, message = "El monto de la transacción no puede ser menor que 0")
	@Schema(description = "Monto de transacción")
	private double price;

	public TransactionDTO() {

	}

	public TransactionDTO(String cardId, double price) {
		this.cardId = cardId;
		this.price = price;
	}

}
