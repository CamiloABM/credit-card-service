package com.bank.card.model;

import com.bank.card.helper.CardStates;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/* Número Tarjeta */
	@NotNull(message = "El número de tarjeta es obligatorio")
	@NotBlank(message = "El número de tarjeta es obligatorio")
	@Pattern(regexp = "\\d{16}", message = "El número de tarjeta debe contener 16 dígitos numericos.")
	@Schema(description = "Número de tarjeta (16 digitos númericos)")
	private String cardId;

	/* Saldo de la tarjeta */
	@Min(value = 0, message = "El saldo no puede ser menor que 0")
	@Schema(description = "Saldo de la tarjeta")
	private double balance;

	/* Primer nombre titular */
	@NotBlank(message = "El nombre del titular es obligatorio")
	@Schema(description = "Primer nomnbre titular")
	private String firstName;

	/* Primer apellido titular */
	@NotBlank(message = "El apellido del titular es obligatorio")
	@Schema(description = "Segundo nombre titular")
	private String lastName;

	/* Tipo de producto */
	@Schema(description = "Numero de producto (6 digitos númericos)")
	private String productId;

	/* Mes de vencimiento */
	@Schema(description = "Mes de vencimiento")
	private int expirationMonth;

	/* Año de vencimiento */
	@Schema(description = "Año de vencimiento")
	private int expirationYear;

	/* Estados */
	@Schema(description = "Estado de la tarjeta")
	private CardStates state;

}
