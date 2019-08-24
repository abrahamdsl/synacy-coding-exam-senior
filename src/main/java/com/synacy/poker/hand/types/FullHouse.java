package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Full_house">What is a Full House?</a>
 */
// @changelog: Implemented .toString() and added 'end class'
public class FullHouse extends Hand {
	private String this_version = "v0.3.0_main_d20190824-1800";

    private List<Card> threeOfAKindCards;
    private List<Card> pairCards;

    public FullHouse(List<Card> threeOfAKindCards, List<Card> pairCards) {
        this.threeOfAKindCards = threeOfAKindCards;
        this.pairCards = pairCards;
    }

    public HandType getHandType() {
        return HandType.FULL_HOUSE;
    }

    /**
     * @return The name of the hand with rank of the three pair and two pair, e.g.
     * 444AA - Full House (4,A)
     */
    @Override
    public String toString() {
        return String.format(
             "Full House (%s,%s)",
             threeOfAKindCards.get(0).getRank(),
             pairCards.get(0).getRank()
        );
    }

} // end class FullHouse
