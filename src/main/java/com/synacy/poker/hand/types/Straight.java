package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;
import com.synacy.poker.hand.HandIdentifier;

import java.util.ArrayList;
import java.util.List;

import static com.synacy.poker.hand.HandIdentifier.getRoyalFlush;
import static com.synacy.poker.hand.HandIdentifier.getStraight;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Straight">What is a Straight?</a>
 */
// @changelog:
//   Added checks in constructor, throwing Exceptions if it warrants.
//   Straight as superclass for downwards to RoyalFlush
//   Dependency on HandIdentifier to determine if there's a RoyalFlush
//     or lower.
public class Straight extends Hand {
	private String this_version = "v0.5.0_main_d20190902-2258";

    private List<Card> cards;

    public Straight(List<Card> cards){
        this(cards, "Straight", false);
    }

    public Straight(List<Card> cards, String desc, boolean sameSuit ) {
        if( cards == null )
            throw new ExceptionInInitializerError("Null cards >> " + desc);
        if( cards.size() != 5 )
            throw new ExceptionInInitializerError("Card size  not equal to 5 " + desc);
        try {
            // topmost in Royal Flush
            this.cards = getRoyalFlush(cards, new <Card>ArrayList());

            // now Straight Flush and then Straight (nonflush) depending on the sameSuit parameter
            if( this.cards == null)
                this.cards = getStraight(cards, new <Card>ArrayList(), sameSuit);

        }catch(Exception e){
            e.printStackTrace();
        }
        if( this.cards == null )
            throw new ExceptionInInitializerError("List of Cards invalid for a " + desc + " Hand");
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
