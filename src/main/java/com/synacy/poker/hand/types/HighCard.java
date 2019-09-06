package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.HandType;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#High_card">What is a High Card?</a>
 */
// @changelog: new method compareTo(Hand), getCards(), getTopRank(), constructor to explicitly throw
//   ExceptionInInitializerError
public class HighCard extends Hand {
	private String this_version = "v0.6.0_main_d20190906-2200";

    private List<Card> cards ;

    public HighCard(List<Card> cards)
    throws ExceptionInInitializerError {
        if( cards == null)
            throw new ExceptionInInitializerError("Null Card List");
        if( cards.size() == 0 )
            throw new ExceptionInInitializerError("Empty Card List");

        this.cards = HandIdentifier.getHighCard( cards, new ArrayList<Card>() );
        if( this.cards == null)
            throw new ExceptionInInitializerError("Something terribly went wrong, please check getHighCard()");
    }

    @Override
    public int compareTo(Hand anotherHand){
      if( this.getRankInOrdinalOrder() < anotherHand.getRankInOrdinalOrder() )
        return -1;
      else
      if( this.getRankInOrdinalOrder() > anotherHand.getRankInOrdinalOrder() )
        return 1;
      else{
        if( anotherHand instanceof HighCard ) {
          int x = 0;
          List<Card> theseCards = this.getCards();
          List<Card> thoseCards = ((HighCard)anotherHand).getCards();
          int z = theseCards.size();

          for(  ; x<z; x++ ){
            Card thisCard = theseCards.get(x);
            Card thatCard = thoseCards.get(x);
            System.out.printf(
              "\t TIE BREAKER %d ) this: %s - %s, that: %s - %s\n",
              x,
              thisCard,
              thisCard.getRankInOrdinalOrder(),
              thatCard,
              thatCard.getRankInOrdinalOrder()
            );
            if( thisCard.getRankInOrdinalOrder() < thatCard.getRankInOrdinalOrder() )
              return -1;
            else
            if( thisCard.getRankInOrdinalOrder() > thatCard.getRankInOrdinalOrder() )
              return 1;
          }
        }
        return 0;
      }
    } // end method compareTo

    public List<Card> getCards() {
        return cards;
    }

    public HandType getHandType() {
        return HandType.HIGH_CARD;
    }

    public CardRank getTopRank(){
        return cards.get(0).getRank();
    }
    /**
     * @return The cards ordered by descending rank, e.g. A,K,Q,3,2
     */
    @Override
    public String toString() {
        // @todo: Better use the map and shits command later

        return String.format(
            "%s,%s,%s,%s,%s",
            getCards().get(0).getRank(),
            getCards().get(1).getRank(),
            getCards().get(2).getRank(),
            getCards().get(3).getRank(),
            getCards().get(4).getRank()
        );
    } // end method toString

} // end class HighCard
