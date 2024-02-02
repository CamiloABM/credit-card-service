package com.bank.card.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bank.card.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	Optional<Transaction> findByTransactionId(String transactionId);

	@Query(value = "select t.* from `transaction` t join card c ON t.card_id = c.id where t.transaction_id = :transactionId and c.card_id = :idCard", nativeQuery = true)
	Optional<Transaction> findByTransactionIdAndIdCard(String transactionId, String idCard);

}
