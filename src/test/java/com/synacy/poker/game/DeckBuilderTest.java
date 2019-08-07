package com.synacy.poker.game;

import com.synacy.poker.deck.Deck;
import com.synacy.poker.deck.DeckBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckBuilderTest {

	@Test
	public void buildDeck() {
		DeckBuilder deckBuilder = new DeckBuilder();

		Deck deck = deckBuilder.buildDeck();

		assertEquals(52, deck.size());
	}

}