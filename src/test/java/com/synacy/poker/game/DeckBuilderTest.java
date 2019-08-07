package com.synacy.poker.game;

import com.synacy.poker.deck.Deck;
import com.synacy.poker.deck.DeckBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckBuilderTest {
	private String this_version = "v0.0.1_main_d20181121-2358";

	@Test
	public void buildDeck() {
		DeckBuilder deckBuilder = new DeckBuilder();

		Deck deck = deckBuilder.buildDeck();

		assertEquals(52, deck.size());
	}

}
