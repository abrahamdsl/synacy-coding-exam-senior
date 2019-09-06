package com.synacy.poker.card;

import java.util.Objects;

/**
 * The card in a deck. A combination of {@link CardRank} and {@link CardSuit}
 */
// CHANGELOG : New method compareTo(Card)/implements Comparable
public class Card implements Comparable{
	private String this_version = "v0.6.0_main_d20190906-2200";
	// @changelog : See above

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

	@Override
	public int compareTo(Object o){
		if( o instanceof Card ) {
			Card anotherCard = (Card) o;
			if (this.getRankInOrdinalOrder() < anotherCard.getRankInOrdinalOrder() )
				return -1;
			else if (this.getRankInOrdinalOrder() > anotherCard.getRankInOrdinalOrder() )
				return 1;
			else
				return 0;
		}else{
			return 0;
		}
	} // end compareTo

	// Gets rank in ordinal order - as how it's aligned ordinarily in the code.
	// @todo : Ace can be used as one or ten depending on the category.
	public int getRankInOrdinalOrder(){
		return rank.ordinal();
	} // end method getRankInOrdinalOrder

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
