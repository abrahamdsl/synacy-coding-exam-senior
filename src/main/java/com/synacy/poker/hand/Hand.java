package com.synacy.poker.hand;

/**
 * The base class of the different Hands such as {@link com.synacy.poker.hand.types.Flush},
 * {@link com.synacy.poker.hand.types.FullHouse}, etc.
 */
public abstract class Hand {
	private String this_version = "v0.0.1_main_d20181121-2358";

    /**
     * @return The {@link HandType}
     */
    public abstract HandType getHandType();

}
