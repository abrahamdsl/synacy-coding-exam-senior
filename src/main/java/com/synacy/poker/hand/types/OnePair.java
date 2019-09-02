package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#One_pair">What is a One Pair?</a>
 */
// @changelog: in .toString() to adapt to changes in HandIdentifier
public class OnePair extends Hand {
	private String this_version = "v0.5.0_main_d20190902-2258";

    private List<Card> pairCards;
    private List<Card> otherCards;

    public OnePair(List<Card> pairCards, List<Card> otherCards) {
        this.pairCards = pairCards;
        this.otherCards = otherCards;
    }

    public HandType getHandType() {
        return HandType.ONE_PAIR;
    }

    /**
     * @return The name of the hand plus kickers ordered by descending rank, e.g. One Pair (2) - A,K,Q High,
     * or the name of the hand and rank if there are no community cards yet in play, e.g. One Pair (2)
     */
    @Override
    public String toString(){
        String ret = String.format( "One Pair (%s)", pairCards.get(0).getRank().toString() );
        int s = otherCards.size();

        if( ! (otherCards == null || s == 0 ) ){

            ret += String.format(
              " - %s,%s,%s High",
              otherCards.get(s-3).getRank().toString(),
              otherCards.get(s-2).getRank().toString(),
              otherCards.get(s-1).getRank().toString()
            );
        }

        return ret;
    } // end method toString

} // end class OnePair
