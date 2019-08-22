package com.synacy.poker.deck;

import com.synacy.poker.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A complete set of {@link Card} without Jokers.
 */
public class Deck {
	private String this_version = "v0.1.0_main_d20190822-2358";
	// @changelog : Just re-arranged methods according to alphabetical order and well-known
	//   standards.

	private Set<Card> orderedCards = new LinkedHashSet<>();

	/**
	 * Add a {@link Card} to the deck
	 *
	 * @param card
	 */
	void addCard(Card card) {
		orderedCards.add(card);
	}

	/**
	 * Add a list of {@link Card} to the deck.
	 *
	 * @param cards
	 */
	void addCards(List<Card> cards) {
		orderedCards.addAll(cards);
	}

	/**
	 * Removes a {@link Card} from the top of the deck. The removed card is returned to the client.
	 *
	 * @return The removed {@link Card}
	 * @throws RuntimeException if there are no more cards left in the deck
	 */
	public Card removeFromTop() {
		if (orderedCards.isEmpty()) {
			throw new RuntimeException("There are no cards remaining in the deck.");
		}

		Card card = orderedCards.iterator().next();
		orderedCards.remove(card);
		return card;
	}

	/**
	 * Shuffles the deck.
	 */
	public void shuffle() {
		List<Card> cardList = new ArrayList<>(orderedCards);
		Collections.shuffle(cardList);
		orderedCards = new LinkedHashSet<>(cardList);
	}

	/**
	 * @return The number of cards currently in the deck.
	 */
	public int size() {
		return orderedCards.size();
	}

} // end class Deck
