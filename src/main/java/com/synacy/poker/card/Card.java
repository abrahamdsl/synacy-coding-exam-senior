package com.synacy.poker.card;

import java.util.Objects;

/**
 * The card in a deck. A combination of {@link CardRank} and {@link CardSuit}
 */
public class Card {
	private String this_version = "v0.1.0_main_d20190822-2358";
	// @changelog : Just re-arranged methods according to alphabetical order and well-known
	//   standards.

	private CardRank rank;
	private CardSuit suit;

	public Card(CardRank rank, CardSuit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Card card = (Card) o;
		return rank == card.rank &&
				suit == card.suit;
	}

	/**
	 * @return The {@link CardRank}
	 */
	public CardRank getRank() {
		return rank;
	}

	/**
	 * @return The {@link CardSuit}
	 */
	public CardSuit getSuit() {
		return suit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(rank, suit);
	}

	/**
	 * @return The CSS class of the card, e.g. <code>card-red</code>
	 */
	public String styleClass() {
		return "card-" + suit.getColor();
	}

	/**
	 * @return The combination of the {@link CardRank} and {@link CardSuit}, e.g. Ace of Hearts is
	 * <code>A&hearts;</code>
	 */
	public String toString() {
		return getRank().toString() + getSuit().toString();
	}

} // end class Card
