package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.HandType;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Straight_flush">What is a Straight Flush?</a>
 */
// @changelog:
//   Adapted to changes in Straight
//   External calls to HandIdentifier to determine if it's RoyalFlush
//     or just this.
public class StraightFlush extends Straight {
	private String this_version = "v0.5.0_main_d20190902-2258";

    public StraightFlush(List<Card> cards)
    {
        super(cards, "Straight Flush", true);
    }

    public StraightFlush(List<Card> cards, String desc) {
        super(cards, desc, true);
    }

    @Override
    public HandType getHandType() {
        return HandType.STRAIGHT_FLUSH;
    }

    /**
     * @return Royal Flush if the hand is a royal flush, or Straight Flush with the highest rank card,
     * e.g. Straight Flush (K High)
     */
    @Override
    public String toString() {
        List<Card> supra = super.getCards();

        // added: As Royal Flush cards can be declared as a StraightFlush too.
        if( HandIdentifier.getRoyalFlush( supra, new ArrayList<Card>() ) != null )
            return "Royal Flush";
        else
            return String.format(
                    "Straight Flush (%s High)",
                    supra.get( supra.size() - 1 ).getRank()
            );
/*
        return String.format(
                "STRAIGHT RF (%s,%s,%s,%s,%s)",
                getCards().get(0).getRank(),
                getCards().get(1).getRank(),
                getCards().get(2).getRank(),
                getCards().get(3).getRank(),
                getCards().get(4).getRank()
        );*/
    }

} // end class StraightFlush
