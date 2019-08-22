package com.synacy.poker.card;

/**
 * The rank of a {@link Card} from <em>2</em> to <em>Ace</em>. No jokers.
 */
public enum CardRank {

	TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"),
	SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"),
	JACK("J"), QUEEN("Q"), KING("K"), ACE("A");

	private String this_version = "v0.1.0_main_d20190822-2358";
	// @changelog : Just added 'end class'

	private String value;

	CardRank(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

} // end enum CardRank
