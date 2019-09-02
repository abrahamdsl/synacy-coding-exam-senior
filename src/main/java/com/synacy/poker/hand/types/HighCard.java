package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.HandType;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#High_card">What is a High Card?</a>
 */
// @changelog:
//   Added checks in constructor, throwing Exceptions if it warrants.
//   Simpler .toString() and to adapt to changes in HandIdentifier
public class HighCard extends Hand {
	private String this_version = "v0.5.0_main_d20190902-2258";

    private List<Card> cards ;

    public HighCard(List<Card> cards) {
        if( cards == null)
            throw new ExceptionInInitializerError("Null Card List");
        if( cards.size() == 0 )
            throw new ExceptionInInitializerError("Empty Card List");

        this.cards = HandIdentifier.getHighCard( cards, new ArrayList<Card>() );
        if( this.cards == null)
            throw new ExceptionInInitializerError("Something terribly went wrong, please check getHighCard()");
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

        return String.format(
            "%s,%s,%s,%s,%s",
            cards.get(0).getRank(),
            cards.get(1).getRank(),
            cards.get(2).getRank(),
            cards.get(3).getRank(),
            cards.get(4).getRank()
        );
    } // end method toString

} // end class HighCard
