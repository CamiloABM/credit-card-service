package com.bank.card.service;

import com.bank.card.dto.TransactionAnulationDTO;
import com.bank.card.dto.TransactionDTO;
import com.bank.card.model.Transaction;

public interface ITransaction {

	public Transaction purchase(TransactionDTO data);

	public Transaction consult(String transactionId);

	public Transaction anulation(TransactionAnulationDTO transactionId);

}
