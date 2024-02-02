package com.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.bank.card.config.ServiceConfig;
import com.bank.card.controller.CardController;
import com.bank.card.controller.TransactionController;
import com.bank.card.dto.CardCreationDTO;
import com.bank.card.dto.CardDTO;
import com.bank.card.dto.CardRechargeDTO;
import com.bank.card.dto.TransactionAnulationDTO;
import com.bank.card.dto.TransactionDTO;
import com.bank.card.helper.CardStates;
import com.bank.card.helper.TransactionStates;
import com.bank.card.model.Card;
import com.bank.card.model.Transaction;

@SpringBootTest(classes = CardController.class)
@ContextConfiguration(classes = { ServiceConfig.class })
class CreditCardServiceApplicationTests {

	/* Probamos desde controlador... */
	@Autowired
	private CardController cardController;
	
	/* Probamos desde controlador... */
	@Autowired
	private TransactionController transactionController;

	@Test
	void contextLoads() {
	}

	@Test
	void executeAll() {
		CardCreationDTO cardCreationDTO = new CardCreationDTO("123456", "CAMILO (PRUEBA)", "BARRETO (PRUEBA)");

		Card card = cardController.generateCardNumber(cardCreationDTO.getProductId(), cardCreationDTO).getBody();

		/*
		 * La creación inicial debe quedar con estado inactivo hasta que se haga la
		 * activación...
		 */
		assertEquals(card.getState(), CardStates.INACTIVE);
		/* Saldo en 0 ... */
		assertEquals(card.getBalance(),0);

		/* Activamos tarjeta... */
		CardDTO cardDTO = new CardDTO(card.getCardId());
		card = cardController.activateCard(cardDTO).getBody();
		assertEquals(card.getState(), CardStates.ACTIVE);

		/* Le abonamos saldo... */
		double recharge = 3000;
		CardRechargeDTO cardRechargeDTO = new CardRechargeDTO(card.getCardId(), recharge);
		card = cardController.rechargeCard(cardRechargeDTO).getBody();
		assertEquals(card.getBalance(), recharge);

		/* Consultamos tarjeta */
		assertEquals(cardController.consultCard(card.getCardId()).getBody(), card);
		
		
		// ============================================================
		// Ejecutamos transacción de 2000...
		double transactionValue = 2000;
		TransactionDTO transactionDTO = new TransactionDTO(card.getCardId(), transactionValue);
		Transaction transaction = transactionController.purchase(transactionDTO).getBody();
		assertEquals(transaction.isSuccessful(), true);
		assertEquals(transaction.getState(), TransactionStates.SUCCESSFUL);
		
		// Verificamos saldo de la cuenta -- le quedan 1000
		double currentBalance = 1000;
		assertEquals(cardController.consultCard(card.getCardId()).getBody().getBalance(), currentBalance);

		
		// Reversamos transacción....
		TransactionAnulationDTO transactionAnulationDTO = new TransactionAnulationDTO(card.getCardId(), transaction.getTransactionId());
		transaction = transactionController.anulation(transactionAnulationDTO).getBody();
		assertEquals(transaction.getState(), TransactionStates.REVERSED);
		
		// Validamos saldo de la cuenta...
		card = cardController.consultCard(card.getCardId()).getBody();
		assertEquals(card.getBalance(), transaction.getTransactionValue() + currentBalance);

		// Intentamos hacer transacción fallida...
		transactionDTO = new TransactionDTO(card.getCardId(), 5000);
		transaction = transactionController.purchase(transactionDTO).getBody();
		assertEquals(transaction.isSuccessful(), false);
		assertEquals(transaction.getState(), TransactionStates.FAILED);

		// Validamos saldo de la cuenta...
		card = cardController.consultCard(card.getCardId()).getBody();
		assertEquals(card.getBalance(), recharge);
		
		/* Bloquemaos tarjeta.... */
		card = cardController.blockCard(card.getCardId()).getBody();
		assertEquals(card.getState(), CardStates.BLOCKED);

		System.out.println("OK...");
	}

}
