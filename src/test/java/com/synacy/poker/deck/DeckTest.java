package com.synacy.poker.deck;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.card.CardSuit;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class DeckTest {
	private String this_version = "v0.1.0_main_d20190822-2358";
	// @changelog : Just added 'end class' indicator, re-arranged functions alphabetically

	private Deck deck;

	@Before
	public void setUp() {
		deck = new Deck();
	}

	@Test
	public void addCard() {
		Card card = new Card(CardRank.ACE, CardSuit.HEARTS);

		deck.addCard(card);

		assertEquals(1, deck.size());
	}

	@Test
	public void constructor_initializesWithNoCards() {
		assertEquals(0, deck.size());
	}

	@Test
	public void removeFromTop_removeCardFromTopOfDeck() {
		Card expectedTopCard = new Card(CardRank.ACE, CardSuit.HEARTS);
		Card bottomCard = new Card(CardRank.ACE, CardSuit.DIAMONDS);
		Card actualRemovedTopCard;

		deck.addCards(Arrays.asList(expectedTopCard, bottomCard));
		actualRemovedTopCard = deck.removeFromTop();

		assertEquals(expectedTopCard, actualRemovedTopCard);
	}

	@Test
	public void shuffle_returnsShuffledDeckOfEqualSize() {
		Card aceClubs = new Card(CardRank.ACE, CardSuit.CLUBS);
		Card aceSpades = new Card(CardRank.ACE, CardSuit.SPADES);

		Card aceDiamonds = new Card(CardRank.ACE, CardSuit.DIAMONDS);
		Card aceHearts = new Card(CardRank.ACE, CardSuit.HEARTS);

		deck.addCards(Arrays.asList(aceHearts, aceDiamonds, aceClubs, aceSpades));
		deck.shuffle();

		assertEquals(4, deck.size());
	}

} // end class DeckTest
