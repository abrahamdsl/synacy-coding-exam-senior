package com.synacy.poker.deck;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.card.CardSuit;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A service class used to build a {@link Deck}
 */
@Component
public class DeckBuilder {
	private String this_version = "v0.1.0_main_d20190822-2358";
	// @changelog : Just re-arranged variables methods according to alphabetical order and well-known
	//   standards.

	/**
	 * Builds a complete {@link Deck} without Jokers. Does not shuffle the deck.
	 *
	 * @return a {@link Deck} of {@link Card}
	 */
	public Deck buildDeck() {
		Deck deck = new Deck();
		deck.addCards(generateCards());

		return deck;
	}

	private List<Card> cardsForSuit(CardSuit suit) {
		CardRank[] cardRanks = CardRank.values();

		return Arrays.stream(cardRanks)
				.map(rank -> new Card(rank, suit))
				.collect(Collectors.toList());
	}

	private List<Card> generateCards() {
		CardSuit[] cardSuits = CardSuit.values();

    // @comment adsllave : Looks like just a foreach
		return Arrays.stream(cardSuits)
				.map(this::cardsForSuit)
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}

} // end class DeckBuilder
