package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;
import com.synacy.poker.hand.HandIdentifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Flush">What is a flush?</a>
 */
// @changelog: new method compareTo(Hand), getTopRank(), utilisation of latter in .toString()
public class Flush extends Hand {
	private String this_version = "v0.6.0_main_d20190906-2200";

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

    @Override
    public int compareTo(Hand anotherHand){
      if( this.getRankInOrdinalOrder() < anotherHand.getRankInOrdinalOrder() )
        return -1;
      else
      if( this.getRankInOrdinalOrder() > anotherHand.getRankInOrdinalOrder() )
        return 1;
      else{
        Card leading = cards.get(0);

        for( Card current : cards.subList( 1, cards.size() ) ) {
          if( leading.getRankInOrdinalOrder() > current.getRankInOrdinalOrder() )
            return 1;
          else
          if( leading.getRankInOrdinalOrder() < current.getRankInOrdinalOrder() )
            return -1;
          else
            leading = current;
        }
        return 0;
      }
    } // end method compareTo

    public HandType getHandType() {
        return HandType.FLUSH;
    }

    public List<Card> getCards() {
        return cards;
    }

    public CardRank getTopRank(){
        return cards.get( cards.size() - 1 ).getRank();
    }

    /**
     * @return Returns the name of the hand and the highest card, e.g. Flush (K High)
     */
    @Override
    public String toString() {
        return String.format(
                "Flush (%s High)",
                getTopRank()
            );
    }

} // end class Flush
