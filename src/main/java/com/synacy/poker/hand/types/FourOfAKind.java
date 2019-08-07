package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Four_of_a_kind">What is a Four of a Kind?</a>
 */
public class FourOfAKind extends Hand {

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
        return "";
    }

}
