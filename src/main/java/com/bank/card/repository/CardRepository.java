package com.bank.card.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.card.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

	public Optional<Card> findByCardId(String cardId);

}
