package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.HandType;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Three_of_a_kind">What is a Three of a Kind?</a>
 */
// @changelog: new method compareTo(Hand), getOtherCards(), getTopRank(), utilisation of latter in .toString()
//   throw exceptions if inappropriate construction encountered.
public class ThreeOfAKind extends Hand {
	private String this_version = "v0.6.0_main_d20190906-2200";

    private List<Card> threeOfAKindCards;
    private List<Card> otherCards;

    public ThreeOfAKind(List<Card> threeOfAKindCards, List<Card> otherCards) {
        this.threeOfAKindCards = threeOfAKindCards;
        this.otherCards = otherCards;

        List<Card> dummy = HandIdentifier.getThreeOfAKind( threeOfAKindCards, otherCards );

        if( dummy == null || dummy.size() < 3 )
            throw new ExceptionInInitializerError("Something terribly went wrong, please check getThreeOfAKind()");
        dummy.clear();
    } // end constructor

    @Override
    public int compareTo(Hand anotherHand) {
      // first compare hand types
      if( this.getRankInOrdinalOrder() < anotherHand.getRankInOrdinalOrder() )
        return -1;
      else
      if( this.getRankInOrdinalOrder() > anotherHand.getRankInOrdinalOrder() )
        return 1;
      else{
        // Now, same hand type, compare the defining logic

        if( this.getTopRank().ordinal() < anotherHand.getTopRank().ordinal() )
          return -1;
        else
        if( this.getTopRank().ordinal() > anotherHand.getTopRank().ordinal() )
          return 1;
        else{
          // Still same, with the top rank. Next, check the highcards.

          int z = -2;

          if( anotherHand instanceof ThreeOfAKind ){
            ThreeOfAKind compareMe = (ThreeOfAKind) anotherHand;
            // iterate over the otherCards to see
            if( getOtherCards().size() != compareMe.getOtherCards().size() ){
              System.err.println("Non matching sizes");
            }else{
              z = getOtherCards().size();
            }

            for( int x = 0; x < z; x++ ){
              Card thisHandCard = this.getOtherCards().get(x);
              Card otherHandCard = compareMe.getOtherCards().get(x);

              int res = thisHandCard.compareTo( otherHandCard );
              if( res != 0 )
                return res;
            }
          }
          // if reached here, exact same ranks.
          return 0;
        }
      }
    } // end method compareTo

    public HandType getHandType() {
        return HandType.THREE_OF_A_KIND;
    }

    public List<Card> getOtherCards(){
        return this.otherCards;
    }

    public CardRank getTopRank(){
        return threeOfAKindCards.get(0).getRank();
    }
    /**
     * @return The name of the hand plus kickers in descending rank, e.g. Trips (4) - A,2 High
     */
    @Override
    public String toString()
    {
        String ret = "";

        ret =  String.format(
                "Trips (%s)",
                getTopRank()
        );

        if( otherCards != null ) {
            int y = otherCards.size() - 1;
            int x  = y - 1;

            if( x > -1 ) {
                ret += String.format(
                        " - %s,%s High",
                        otherCards.get(x).getRank().toString(),
                        otherCards.get(y).getRank().toString()
                );
            }
        }

        return ret;
    } // end method toString

} // end class ThreeOfAKind
