package com.bank.card.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.card.dto.CardCreationDTO;
import com.bank.card.dto.CardDTO;
import com.bank.card.dto.CardRechargeDTO;
import com.bank.card.exception.CustomException;
import com.bank.card.helper.CardStates;
import com.bank.card.helper.Util;
import com.bank.card.model.Card;
import com.bank.card.repository.CardRepository;
import com.bank.card.service.ICard;

//import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.java.Log;

/**
 * Clase encargada de gestionar productos tarjetas....
 * 
 * @author Camilo Barreto
 * @version 1.0
 */
@Log
@Service
public class CardImpl implements ICard {

	@Autowired
	CardRepository cardRepository;

	@Autowired
	Util util;

	/**
	 * Crea el producto tarjeta en la base de datos y le asigna estado inactivo...
	 */
	@Override
	public Card generateCardNumber(@Valid CardCreationDTO cardCreationDTO) {
		String cardId = cardCreationDTO.getProductId() + util.generateCardNumber();
		log.info("Creación de tarjeta con número: " + cardId);
		Card card = new Card();
		card.setCardId(cardId);
		card.setBalance(0);
		card.setExpirationMonth(util.getExpirationMonth());
		card.setExpirationYear(util.getExpirationYear());
		card.setFirstName(cardCreationDTO.getFirstName());
		card.setLastName(cardCreationDTO.getLastName());
		card.setProductId(cardCreationDTO.getProductId());
		card.setState(CardStates.INACTIVE);
		return cardRepository.save(card);
	}

	
	/**
	 *	Cambia de estado ACTIVO a una tarjeta que exista...
	 */
	@Override
	public Card activateCard(@Valid CardDTO data) {
		Card card = findCard(data.getCardId());
		card.setState(CardStates.ACTIVE);
		return cardRepository.save(card);
	}

	/**
	 *	Cambia de estado BLOQUEADO a una tarjeta que exista...
	 */
	@Override
	public Card blockCard(@Valid CardDTO data) {
		Card card = findCard(data.getCardId());
		card.setState(CardStates.BLOCKED);
		return cardRepository.save(card);
	}

	
	/**
	 * Recarga saldo a una tarheta que exista y tenga estado activo...
	 */
	@Override
	public Card rechargeCard(@Valid CardRechargeDTO data) {
		Card card = findCard(data.getCardId());
		validateCardState(card);
		card.setBalance(card.getBalance() + data.getBalance());
		return cardRepository.save(card);
	}

	
	/**
	 *	Consulta tarjeta por identificador...
	 */
	@Override
	public Card consultCard(CardDTO data) {
		return findCard(data.getCardId());
	}

	
	/**
	 * Busca una tarjeta en la base de datos...
	 * 
	 * @param cardId
	 * @return
	 */
	public Card findCard(String cardId) {
		Optional<Card> cardRepo = cardRepository.findByCardId(cardId);
		if (!cardRepo.isPresent())
			throw new CustomException("Número de tarjeta [" + cardId + "] no encontrado.");
		return cardRepo.get();
	}
	
	/**
	 * Verifica que la tarjeta tenga estado activo, en caso de que no genera excepción...
	 * @param card
	 */
	public void validateCardState(Card card) {
		if (card.getState() != CardStates.ACTIVE) {
			throw new CustomException(
					"Número de tarjeta [" + card.getCardId() + "] tiene estado invalido. [" + card.getState() + "]");
		}
	}

}
