package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
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
// @changelog: new method compareTo(Hand), getTopRank(), utilisation of latter in .toString(),
//   some spacing adjustments
//   some minor output correction
//   explicitly throw ExceptionInInitializerError in constructor if needed
public class Straight extends Hand {
	private String this_version = "v0.6.0_main_d20190906-2200";

    private List<Card> cards;
    public final String straightType;

    public Straight(List<Card> cards){
        this( cards, "Straight", false );
    }

    public Straight( List<Card> cards, String desc, boolean sameSuit )
    throws ExceptionInInitializerError {
        if( cards == null )
            throw new ExceptionInInitializerError("Null cards");
        if( cards.size() != 5 )
            throw new ExceptionInInitializerError( "Card size not equal to 5 for a " + desc );
        try {
            // topmost in Royal Flush
            this.cards = getRoyalFlush( cards, new <Card>ArrayList() );

            // now Straight Flush and then Straight (nonflush) depending on the sameSuit parameter
            if( this.cards == null)
                this.cards = getStraight( cards, new <Card>ArrayList(), sameSuit );
        }catch(Exception e){
            e.printStackTrace();
        }
        if( this.cards == null )
            throw new ExceptionInInitializerError( "List of Cards invalid for a " + desc + " Hand" );

        straightType = desc;
    } // end constructor

    @Override
    public int compareTo(Hand anotherHand){
      if( this.getRankInOrdinalOrder() < anotherHand.getRankInOrdinalOrder() )
        return -1;
      else
      if( this.getRankInOrdinalOrder() > anotherHand.getRankInOrdinalOrder() )
        return 1;
      else{
        if( anotherHand instanceof Straight ){
          int x = 0;
          List<Card> theseCards = this.getCards();
          List<Card> thoseCards = ((Straight)anotherHand).getCards();
          int z = theseCards.size();

          for(  ; x<z; x++ ){
            Card thisCard = theseCards.get(x);
            Card thatCard = thoseCards.get(x);
            System.out.printf(
              "\t[d] TIE BREAKER %d ) this: %s - %s, that: %s - %s\n",
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

          return 0;
        } // end if-else if( anotherHand instanceof Straight )
        return 0;
      } // end hands ranks order
    } // end method compareTo

    public HandType getHandType() {
        return HandType.STRAIGHT;
    }

    public List<Card> getCards() {
        return cards;
    }

    public CardRank getTopRank(){
        return cards.get( cards.size() - 1 ).getRank();
    }

    /**
     * @return The name of the hand and the high card, e.g. Straight (A High)
     */
    @Override
    public String toString()
    {
        return String.format(
                "Straight (%s High)",
                getTopRank()
        );
    } // end method toString

} // end class Straight
