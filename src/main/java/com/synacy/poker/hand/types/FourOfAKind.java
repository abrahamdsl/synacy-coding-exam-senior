package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Four_of_a_kind">What is a Four of a Kind?</a>
 */
// @changelog : Implemented .toString() and added 'end class'
public class FourOfAKind extends Hand {
	private String this_version = "v0.3.0_main_d20190824-1800";

    private List<Card> fourOfAKindCards;
    private List<Card> otherCards;

    public FourOfAKind(List<Card> fourOfAKindCards, List<Card> otherCards) {
        this.fourOfAKindCards = fourOfAKindCards;
        this.otherCards = otherCards;
    }

    public HandType getHandType() {
        return HandType.FOUR_OF_A_KIND;
    }

    /**
     * @return Returns the name of the hand plus kicker, e.g. Quads (4) - A High
     */
    @Override
    public String toString() {
        return String.format(
                "Quads (%s) - %s High",
                fourOfAKindCards.get(0).getRank(),
                otherCards.get( otherCards.size() - 1).getRank()
        );
    }

} // end class FourOfAKind
