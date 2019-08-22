package com.synacy.poker.game;

import com.synacy.poker.deck.Deck;
import com.synacy.poker.deck.DeckBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckBuilderTest {
	private String this_version = "v0.1.0_main_d20190822-2358";
	// @changelog : Just added 'end class' indicator,

	@Test
	public void buildDeck() {
		DeckBuilder deckBuilder = new DeckBuilder();

		Deck deck = deckBuilder.buildDeck();

		assertEquals(52, deck.size());
	}

} //end class DeckBuilderTest
