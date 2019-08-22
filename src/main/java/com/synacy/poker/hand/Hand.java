package com.synacy.poker.hand;

/**
 * The base class of the different Hands such as {@link com.synacy.poker.hand.types.Flush},
 * {@link com.synacy.poker.hand.types.FullHouse}, etc.
 */
public abstract class Hand {
    private String this_version = "v0.1.0_main_d20190822-2358";
    // @changelog : added 'end class'

    /**
     * @return The {@link HandType}
     */
    public abstract HandType getHandType();

} // end class Hand
