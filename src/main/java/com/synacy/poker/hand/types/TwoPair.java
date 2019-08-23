package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Two_pair">What is a Two Pair?</a>
 */
// Changelog: implemented .toString(), added 'end class'
public class TwoPair extends Hand {
	private String this_version = "v0.2.0_main_d20190823-2358";


    private List<Card> firstPairCards;
    private List<Card> secondPairCards;
    private List<Card> otherCards;

    public TwoPair(List<Card> firstPairCards, List<Card> secondPairCards, List<Card> otherCards) {
        this.firstPairCards = firstPairCards;
        this.secondPairCards = secondPairCards;
        this.otherCards = otherCards;
    }

    public HandType getHandType() {
        return HandType.TWO_PAIR;
    }

    /**
     * @return The name of the hand with kicker ranked in descending order, e.g. Two Pair (4,3) - A High
     */
    @Override
    public String toString() {
        int y = otherCards.size() - 1;
        return String.format(
                "Two Pair (%s,%s) - %s High",
                firstPairCards.get(0).getRank().toString(),
                secondPairCards.get(0).getRank().toString(),
                otherCards.get(y).getRank().toString()
        );
    } // end method toString()

} // end class TwoPair
