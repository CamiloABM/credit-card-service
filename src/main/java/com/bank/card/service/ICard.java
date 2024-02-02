package com.bank.card.service;

import com.bank.card.dto.CardCreationDTO;
import com.bank.card.dto.CardDTO;
import com.bank.card.dto.CardRechargeDTO;
import com.bank.card.model.Card;

public interface ICard {

	public Card generateCardNumber(CardCreationDTO productId);

	public Card activateCard(CardDTO data);

	public Card blockCard(CardDTO data);

	public Card rechargeCard(CardRechargeDTO data);

	public Card consultCard(CardDTO data);

}
