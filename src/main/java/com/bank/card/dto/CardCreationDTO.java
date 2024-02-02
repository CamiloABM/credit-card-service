package com.bank.card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CardCreationDTO {

	@NotBlank(message = "El campo 'productId' no puede estar vacío ni ser nulo")
	@Pattern(regexp = "\\d{6}", message = "El producto de la tarjeta debe contener 6 dígitos numericos.")
	private String productId;

	/* Primer nombre titular */
	@NotBlank(message = "El campo 'firstName' no puede estar vacío ni ser nulo")
	private String firstName;

	/* Primer apellido titular */
	@NotBlank(message = "El campo 'lastName' no puede estar vacío ni ser nulo")
	private String lastName;

	public CardCreationDTO() {

	}

	public CardCreationDTO(String productId, String firstName, String lastName) {
		this.productId = productId;
		this.firstName = firstName;
		this.lastName = firstName;

	}

}
