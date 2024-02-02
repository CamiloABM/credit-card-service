package com.bank.card.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.card.dto.TransactionAnulationDTO;
import com.bank.card.dto.TransactionDTO;
import com.bank.card.model.Transaction;
import com.bank.card.service.ITransaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("transaction")
@Tag(name = "Gestión de transacciones", description = "Ejecuta, consulta y anula transacciones")
public class TransactionController {

	@Autowired
	ITransaction transaction;

	@PostMapping("purchase")
	@Operation(description = "Ejecutar transacción")
	public ResponseEntity<Transaction> purchase(@RequestBody @Valid TransactionDTO data) {
		return new ResponseEntity<>(transaction.purchase(data), HttpStatus.OK);
	}

	@GetMapping("{transactionId}")
	@Operation(description = "Consultar transacción")
	public ResponseEntity<Transaction> consult(@PathVariable String transactionId) {
		return new ResponseEntity<>(transaction.consult(transactionId), HttpStatus.OK);
	}

	@PostMapping("anulation")
	@Operation(description = "Anular transacción")
	public ResponseEntity<Transaction> anulation(@RequestBody @Valid TransactionAnulationDTO data) {
		return new ResponseEntity<>(transaction.anulation(data), HttpStatus.OK);
	}

}
