package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.HandType;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Flush">What is a Royal Flush?</a>
 */
// @changelog: This class to be deprecated in next major - looks like we really don't need this at all.
public class RoyalFlush extends StraightFlush {
    private String this_version = "v0.6.0_main_d20190906-2200";

    public RoyalFlush(List<Card> cards, String desc) {
        super( cards, "Royal Flush" );
    }

    // @todo : deprecate/remove in next major - use superclass' instead or remove this class altogether
    @Override
    public HandType getHandType() {
        return HandType.ROYAL_FLUSH;
    }

    // @todo : deprecate/remove in next major - use superclass' instead or remove this class altogether
    @Override
    public String toString() {
        return "Royal Flush";
    }

} // end class RoyalFlush
