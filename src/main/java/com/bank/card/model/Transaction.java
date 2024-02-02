package com.bank.card.model;

import java.sql.Timestamp;

import com.bank.card.helper.TransactionStates;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Schema(description = "Identificador transaccional")
	private String transactionId;
	
	@Schema(description = "Mensaje inicial")
	private String message;

	@ManyToOne
	@JoinColumn(name = "card_id")
	private Card cardId;

	@Schema(description = "Valor transacción")
	private double transactionValue;

	@Schema(description = "Fecha transacción")
	private Timestamp  transactionDate;
	
	@Schema(description = "Fecha anulación transacción")
	private Timestamp  transactionDateReverse;
	
	@Schema(description = "Esatdo transacción")
	private TransactionStates state;
	
	@Schema(description = "Fue exitosa")
	private boolean isSuccessful;

}
