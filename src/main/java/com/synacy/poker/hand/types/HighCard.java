package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#High_card">What is a High Card?</a>
 */
// Changelog: implemented .toString(), added 'end class'
public class HighCard extends Hand {
	private String this_version = "v0.2.0_main_d20190823-2358";

    private List<Card> cards ;

    public HighCard(List<Card> cards) {
        this.cards = cards;
    }

    public HandType getHandType() {
        return HandType.HIGH_CARD;
    }

    /**
     * @return The cards ordered by descending rank, e.g. A,K,Q,3,2
     */
    @Override
    public String toString() {
        // @todo: Better use the map and shits command later
        String seq = "";

        for( int y = cards.size() - 1; y > 0; y-- ){
            Card examine = cards.get(y);
            seq += ( examine.getRank().toString() + ",");
        }
        seq += cards.get(0).getRank().toString();

        return seq;
    } // end method toString

} // end class HighCard
