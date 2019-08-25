package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Straight">What is a Straight?</a>
 */
// Changelog: new constructor(s) due to (debugging) changes in Straight
public class Straight extends Hand {
	private String this_version = "v0.4.0_main_d20190825-2358";

    private List<Card> cards;

    public Straight(List<Card> cards){
        this(cards, "Straight Desc Raw");
    }

    public Straight(List<Card> cards, String desc ) {
        if( cards == null )
            throw new ExceptionInInitializerError("Null cards >> " + desc);
        if( cards.size() != 5 )
            throw new ExceptionInInitializerError("Card size  not equal to 5 " + desc);

        this.cards = cards;
    }

    public HandType getHandType() {
        return HandType.STRAIGHT;
    }

    public List<Card> getCards() {
        return cards;
    }

    /**
     * @return The name of the hand and the high card, e.g. Straight (A High)
     */
    @Override
    public String toString()
    {
        return String.format(
                "Straight (%s High)",
                cards.get( cards.size() - 1).getRank().toString()
        );
    } // end method toString

} // end class Straight
