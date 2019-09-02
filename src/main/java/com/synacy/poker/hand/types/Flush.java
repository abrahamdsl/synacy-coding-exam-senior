package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;
import com.synacy.poker.hand.HandIdentifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Flush">What is a flush?</a>
 */
// @changelog: Added checks in constructor, throwing Exceptions if it warrants.
public class Flush extends Hand {
	private String this_version = "v0.5.0_main_d20190902-2258";

    private List<Card> cards;

    // @todo : check for sequences here.
    public Flush(List<Card> cards)
    {
        if( cards == null )
            throw new ExceptionInInitializerError("Null Card List");

        this.cards = HandIdentifier.getFlush(cards, new <Card>ArrayList());

        if( this.cards == null )
            throw new ExceptionInInitializerError("Invalid cards for a Flush Hand");
    }

    public HandType getHandType() {
        return HandType.FLUSH;
    }

    public List<Card> getCards() {
        return cards;
    }

    /**
     * @return Returns the name of the hand and the highest card, e.g. Flush (K High)
     */
    @Override
    public String toString() {
        return String.format(
                "Flush (%s High)",
                cards.get( cards.size() - 1 ).getRank().toString()
            );
    }

} // end class Flush
