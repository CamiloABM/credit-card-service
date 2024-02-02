package com.bank.card.model;

import java.sql.Timestamp;

import com.bank.card.helper.TransactionStates;

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
	
	private String transactionId;
	
	private String message;

	@ManyToOne
	@JoinColumn(name = "card_id")
	private Card cardId;

	private double transactionValue;

	private Timestamp  transactionDate;
	
	private Timestamp  transactionDateReverse;

	private TransactionStates state;
	
	private boolean isSuccessful;

}
