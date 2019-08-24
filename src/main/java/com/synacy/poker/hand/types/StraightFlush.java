package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Straight_flush">What is a Straight Flush?</a>
 */
// @changelog: Implemented .toString(), added 'end class'
public class StraightFlush extends Straight {
	private String this_version = "v0.3.0_main_d20190824-1800";

    public StraightFlush(List<Card> cards) {
        super(cards);
    }

    @Override
    public HandType getHandType() {
        return HandType.STRAIGHT_FLUSH;
    }

    /**
     * @return Royal Flush if the hand is a royal flush, or Straight Flush with the highest rank card,
     * e.g. Straight Flush (K High)
     */
    @Override
    public String toString() {
        List<Card> supra = super.getCards();

        return String.format(
                "Straight Flush (%s High)",
                supra.get( supra.size() - 1 ).getRank()
        );
    }

} // end class StraightFlush
