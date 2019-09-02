package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Three_of_a_kind">What is a Three of a Kind?</a>
 */
// @changelog: Bugfix in .toString() , regarding order of card display
public class ThreeOfAKind extends Hand {
	private String this_version = "v0.5.0_main_d20190902-2258";

    private List<Card> threeOfAKindCards;
    private List<Card> otherCards;

    public ThreeOfAKind(List<Card> threeOfAKindCards, List<Card> otherCards) {
        this.threeOfAKindCards = threeOfAKindCards;
        this.otherCards = otherCards;
    }

    public HandType getHandType() {
        return HandType.THREE_OF_A_KIND;
    }

    /**
     * @return The name of the hand plus kickers in descending rank, e.g. Trips (4) - A,2 High
     */
    @Override
    public String toString()
    {
        int y = otherCards.size() - 1;
        int x  = y - 1;
        return String.format(
                "Trips (%s) - %s,%s High",
                threeOfAKindCards.get(0).getRank().toString(),
                otherCards.get(x).getRank().toString(),
                otherCards.get(y).getRank().toString()
        );
    } // end method toString

} // end class ThreeOfAKind
