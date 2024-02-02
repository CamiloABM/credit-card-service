package com.bank.card.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CardRechargeDTO {

	/* Número Tarjeta */
	@NotNull(message = "El número de tarjeta es obligatorio")
	@NotBlank(message = "El número de tarjeta es obligatorio")
	@Pattern(regexp = "\\d{16}", message = "El número de tarjeta debe contener 16 dígitos numericos.")
	private String cardId;

	@Min(value = 0, message = "El saldo no puede ser menor que 0")
	private double balance;

	public CardRechargeDTO() {
	}

	public CardRechargeDTO(String cardId, double balance) {
		this.cardId = cardId;
		this.balance = balance;
	}

}
