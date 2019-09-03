package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Three_of_a_kind">What is a Three of a Kind?</a>
 */
// @changelog: Bugfix in .toString() : do not expect that otherCards will be always
//   filled in. Added size check during construction.
public class ThreeOfAKind extends Hand {
	private String this_version = "v0.5.2_main_d20190903-2250";

    private List<Card> threeOfAKindCards;
    private List<Card> otherCards;

    public ThreeOfAKind(List<Card> threeOfAKindCards, List<Card> otherCards) {
        this.threeOfAKindCards = threeOfAKindCards;
        this.otherCards = otherCards;

        if( threeOfAKindCards.size() < 3 )
            throw new ExceptionInInitializerError("Invalid 3 of a kind cards - less than 3");
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
        String ret = "";

        ret =  String.format(
                "Trips (%s)",
                threeOfAKindCards.get(0).getRank().toString()
        );

        if( otherCards != null ) {
            int y = otherCards.size() - 1;
            int x  = y - 1;

            if( x > -1 ) {
                ret += String.format(
                        " - %s,%s High",
                        otherCards.get(x).getRank().toString(),
                        otherCards.get(y).getRank().toString()
                );
            }
        }

        return ret;
    } // end method toString

} // end class ThreeOfAKind
