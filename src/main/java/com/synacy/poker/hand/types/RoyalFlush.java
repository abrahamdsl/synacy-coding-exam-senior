package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.HandType;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Flush">What is a Royal Flush?</a>
 */
// @changelog: Now extends StraightFlush versus previous of Straight
public class RoyalFlush extends StraightFlush {
    private String this_version = "v0.5.0_main_d20190902-2258";

    public RoyalFlush(List<Card> cards, String desc) {
        super( cards, "Royal Flush" );
    }

    @Override
    public HandType getHandType() {
        return HandType.ROYAL_FLUSH;
    }

    @Override
    public String toString() {
        return "Royal Flush";
    }

} // end class RoyalFlush
