package com.bank.card.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.card.dto.TransactionAnulationDTO;
import com.bank.card.dto.TransactionDTO;
import com.bank.card.exception.CustomException;
import com.bank.card.helper.CardStates;
import com.bank.card.helper.TransactionStates;
import com.bank.card.helper.Util;
import com.bank.card.model.Card;
import com.bank.card.model.Transaction;
import com.bank.card.repository.CardRepository;
import com.bank.card.repository.TransactionRepository;
import com.bank.card.service.ITransaction;

import lombok.extern.java.Log;

/**
 * Clase encargada de gestionar transacciones de tarjeta....
 * 
 * @author Camilo Barreto
 * @version 1.0
 */
@Log
@Service
public class TransactionImpl implements ITransaction {

	@Autowired
	CardRepository cardRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	Util util;

	@Autowired
	CardImpl cardImpl;

	/**
	 * Ejecutar transacción con sus respectivas validaciones
	 * 
	 *  - Estado de la tarjeta
	 *  - Saldo de la tarjeta
	 *  
	 *  luego de esto se guarda registro en la base de datos...
	 *  
 	 * @param TransactionDTO
	 * @return Transaction 
	 */
	@Override
	@Transactional
	public Transaction purchase(TransactionDTO data) {
		boolean isSuccessful = true;
		Transaction transaction = new Transaction();
		transaction.setMessage("! ! Transacción exitosa ¡ ¡");
		// Validamos que la tarjeta exista:
		Card card = cardImpl.findCard(data.getCardId());
		// Validamos estado:
		if (card.getState() != CardStates.ACTIVE) {
			isSuccessful = false;
			log.warning("Estado de tarjeta invalido...");
			transaction.setMessage("Estado invalido...");
		}
		// Validamos monto de la transacción:
		if (data.getPrice() > card.getBalance()) {
			log.warning("Saldo insuficiente..." + data.getPrice() + " > " + card.getBalance());
			isSuccessful = false;
			transaction.setMessage("Saldo insuficiente...");
		}
		// Guardamos saldo nuevo...
		if (isSuccessful) {
			card.setBalance(card.getBalance() - data.getPrice());
			cardRepository.save(card);
		}
		transaction.setSuccessful(isSuccessful);
		transaction.setCardId(card);
		transaction.setTransactionValue(data.getPrice());
		transaction.setState((isSuccessful) ? TransactionStates.SUCCESSFUL : TransactionStates.FAILED);
		transaction.setTransactionId(util.generateTransacionId());
		transaction.setTransactionDate(util.getCurrentDate());
		// Guardamos registro de transacción...
		return transactionRepository.save(transaction);
	}

	/**
	 * Consultar transacción por @transactionId
	 *
	 */
	@Override
	public Transaction consult(String transactionId) {
		Optional<Transaction> transactionOpt = transactionRepository.findByTransactionId(transactionId);
		if (!transactionOpt.isPresent()) {
			throw new CustomException("Transacción con identificador [" + transactionId + "] no encontrado.");
		}
		return transactionOpt.get();
	}

	/**
	 * 1. Crear el servicio de anulación de transacciones, el cual consiste en que a
	 * partir de la transacción de compra: • Se debe anular a partir del id de
	 * transacción • La transacción a anular no debe ser mayor a 24 horas. • La
	 * transacción quede marcada en anulada. • El valor de la compra debe volver a
	 * estar disponible en el saldo.
	 * 
	 * @param TransactionAnulationDTO
	 * @return Transaction 
	 */
	@Override
	public Transaction anulation(TransactionAnulationDTO transactionId) {
		Optional<Transaction> transactionOpt = transactionRepository
				.findByTransactionIdAndIdCard(transactionId.getTransactionId(), transactionId.getCardId());
		if (!transactionOpt.isPresent()) {
			throw new CustomException("Transacción con identificador [" + transactionId.getTransactionId() + "] no encontrado.");
		}
		Transaction transaction = transactionOpt.get();
		if (transaction.getState() != TransactionStates.SUCCESSFUL) {
			throw new CustomException("Estado de transacción invalido [" + transaction.getState() + "] ");
		}
		/* Verificamos tiempo limite de reverso... */
		validateDifference24Hours(transaction.getTransactionDate());
		/* Se verifica tarjeta y estado */
		Card card = cardImpl.findCard(transactionId.getCardId());
		cardImpl.validateCardState(card);

		/* Se procede a anular la transacción... */
		card.setBalance(card.getBalance() + transaction.getTransactionValue());
		cardRepository.save(card);

		/* Actualizamos transacción */
		transaction.setTransactionDateReverse(Timestamp.valueOf(LocalDateTime.now()));
		transaction.setState(TransactionStates.REVERSED);

		return transactionRepository.save(transaction);
	}

	
	
	/**
	 * Metodo de validación diferencia en horas de dos fechas...
	 * 
	 * @param transactionDate
	 */
	private void validateDifference24Hours(Timestamp transactionDate) {
		Timestamp currentDate = Timestamp.valueOf(LocalDateTime.now());
		long diferenciaEnHoras = (currentDate.getTime() - transactionDate.getTime()) / (60 * 60 * 1000);
		if (diferenciaEnHoras > 24)
			throw new CustomException("Transacción superó limite de tiempo establecido...");
	}

}
