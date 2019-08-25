package com.synacy.poker.hand;

/**
 * An enum of {@link Hand}s
 *
 * <ul>
 * <li>High Card</li>
 * <li>One Pair</li>
 * <li>Two Pair</li>
 * <li>Three of a Kind</li>
 * <li>Straight</li>
 * <li>Flush</li>
 * <li>Full House</li>
 * <li>Four of a Kind</li>
 * <li>Straight Flush</li>
 * <li>Royal Flush</li>
 * </ul>
 */
 // @changelog: Added Royal Flush
public enum HandType {

    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    STRAIGHT,
    FLUSH,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    STRAIGHT_FLUSH,
    ROYAL_FLUSH;

    private String this_version = "v0.4.0_main_d20190825-2358";
} // end enum HandType
