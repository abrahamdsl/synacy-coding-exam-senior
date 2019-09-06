package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.HandType;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Straight_flush">What is a Straight Flush?</a>
 */
// @changelog: new method getTopRank(), utilisation of latter in .toString(), overhauled .toString(), move most RoyalFlush() functionality to here
//   like changing getHandType()
public class StraightFlush extends Straight {
	private String this_version = "v0.6.0_main_d20190906-2200";

    public StraightFlush(List<Card> cards)
    {
        super(cards, "Straight Flush", true);
    }

    public StraightFlush(List<Card> cards, String desc) {
        super(cards, desc, true);
    }

    @Override
    public HandType getHandType() {
      // added: As Royal Flush cards can be declared as a StraightFlush too.
      if( HandIdentifier.getRoyalFlush( super.getCards(), new ArrayList<Card>() ) != null )
        return HandType.ROYAL_FLUSH;
      else
        return HandType.STRAIGHT_FLUSH;
    }

    public CardRank getTopRank(){
      if( HandIdentifier.getRoyalFlush( super.getCards(), new ArrayList<Card>() ) != null )
          return CardRank.KING;
      else
          //return supra.get( supra.size() - 1 ).getRank();
          return super.getTopRank();
    } // end method getTopRank

    /**
     * @return Royal Flush if the hand is a royal flush, or Straight Flush with the highest rank card,
     * e.g. Straight Flush (K High)
     */
    @Override
    public String toString() {
      // added: As Royal Flush cards can be declared as a StraightFlush too.
      if( getHandType() == HandType.ROYAL_FLUSH )
        return "Royal Flush";
      else
        return String.format( "Straight Flush (%s High)", super.getTopRank() );
    }

} // end class StraightFlush
