package com.synacy.poker.card;

/**
 * A blank card. Also, the back side of the card.
 */
public class BlankCard extends Card {
	private String this_version = "v0.1.0_main_d20190822-2358";
	// @changelog : Just added 'end class'

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

} // end class BlankCard
