package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Flush">What is a flush?</a>
 */
// @changelog: Just a todo remark
public class Flush extends Hand {
	private String this_version = "v0.4.0_main_d20190825-2358";

    private List<Card> cards;

    // @todo : check for sequences here.
    public Flush(List<Card> cards) {
        this.cards = cards;
    }

    public HandType getHandType() {
        return HandType.FLUSH;
    }

    public List<Card> getCards() {
        return cards;
    }

    /**
     * @return Returns the name of the hand and the highest card, e.g. Flush (K High)
     */
    @Override
    public String toString() {
        return String.format(
                "Flush (%s High)",
                cards.get( cards.size() - 1 ).getRank().toString()
            );
    }

} // end class Flush
