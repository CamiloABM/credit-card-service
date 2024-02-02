package com.bank.card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CardDTO {

	/* Número Tarjeta */
	@NotNull(message = "El número de tarjeta es obligatorio")
	@NotBlank(message = "El número de tarjeta es obligatorio")
	@Pattern(regexp = "\\d{16}", message = "El número de tarjeta debe contener 16 dígitos numericos.")
	private String cardId;

	public CardDTO() {
	}

	public CardDTO(String cardId) {
		this.cardId = cardId;
	}

}
