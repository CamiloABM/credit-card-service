package com.bank.card.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.card.dto.CardCreationDTO;
import com.bank.card.dto.CardDTO;
import com.bank.card.dto.CardRechargeDTO;
import com.bank.card.model.Card;
import com.bank.card.service.ICard;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
//import lombok.extern.java.Log;

//@Log
@RestController
@RequestMapping("card")
@Tag(name = "Gestión de tarjetas", description = "Operación basica de tarjetas")
public class CardController {

	@Autowired
	ICard card;

	@GetMapping("{productId}/number")
	@Operation(description = "Crear tarjeta con estado inicial INACTIVO")
	public ResponseEntity<Card> generateCardNumber(@PathVariable String productId,
			@RequestBody @Valid CardCreationDTO data) {
		return new ResponseEntity<>(card.generateCardNumber(data), HttpStatus.CREATED);
	}

	@PostMapping("enroll")
	@Operation(description = "Activar tarjeta")
	public ResponseEntity<Card> activateCard(@Valid @RequestBody CardDTO cardDTO) {
		return new ResponseEntity<>(card.activateCard(cardDTO), HttpStatus.OK);
	}

	@DeleteMapping("{cardId}")
	@Operation(description = "Bloquear tarjeta")
	public ResponseEntity<Card> blockCard(@PathVariable String cardId) {
		CardDTO cardDto = new CardDTO();
		cardDto.setCardId(cardId);
		return new ResponseEntity<>(card.blockCard(cardDto), HttpStatus.OK);
	}

	@PostMapping("balance")
	@Operation(description = "Abonar saldo a tarjeta")
	public ResponseEntity<Card> rechargeCard(@Valid @RequestBody CardRechargeDTO data) {
		return new ResponseEntity<>(card.rechargeCard(data), HttpStatus.OK);
	}

	@GetMapping("balance/{cardId}")
	@Operation(description = "Consultar tarjeta")
	public ResponseEntity<Card> consultCard(@PathVariable String cardId) {
		CardDTO cardDto = new CardDTO();
		cardDto.setCardId(cardId);
		return new ResponseEntity<>(card.consultCard(cardDto), HttpStatus.OK);
	}

}
