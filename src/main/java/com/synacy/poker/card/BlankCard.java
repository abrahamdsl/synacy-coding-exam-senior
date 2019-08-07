package com.synacy.poker.card;

/**
 * A blank card. Also, the back side of the card.
 */
public class BlankCard extends Card {
	String this_version = "v0.0.1_main_d20181121-2358";

	public BlankCard() {
		super(null, null);
	}

	/**
	 *
	 * @return The CSS class <code>card-back</code>
	 */
	@Override
	public String styleClass() {
		return "card-back";
	}

	@Override
	public String toString() {
		return "&nbsp;";
	}

}
