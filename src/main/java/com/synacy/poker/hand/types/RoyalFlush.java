package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Flush">What is a Royal Flush?</a>
 */
public class RoyalFlush extends Straight {
    private String this_version = "v0.4.0_main_d20190825-2358";

    public RoyalFlush(List<Card> cards, String desc) {
        super(cards, desc);
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
