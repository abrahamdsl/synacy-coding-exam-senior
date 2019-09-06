package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.HandType;

import java.util.List;
import java.util.ArrayList;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Two_pair">What is a Two Pair?</a>
 */
// @changelog:
//   new methods compareTo(Hand), getOtherCards(), getSecondPairCards(), getTopRank(), utilisation of latter
//     in .toString()
//   throw exceptions if inappropriate construction encountered.
//   change display - we now have the correct/traditional ordering of cards
public class TwoPair extends Hand {
	private String this_version = "v0.6.0_main_d20190906-2200";
    private List<Card> firstPairCards;
    private List<Card> secondPairCards;
    private List<Card> otherCards;

    public TwoPair(List<Card> firstPairCards, List<Card> secondPairCards, List<Card> otherCards)
    throws ExceptionInInitializerError {
        this.firstPairCards = firstPairCards;
        this.secondPairCards = secondPairCards;
        this.otherCards = otherCards;

        List<Card> completeFive = null;
        List<Card> dummy = null;
        int sizeTotal = 0;

        if( this.firstPairCards == null || this.secondPairCards == null )
            throw new ExceptionInInitializerError("Null Card List");
        sizeTotal = this.firstPairCards.size() + this.secondPairCards.size();
        if( sizeTotal == 0 )
            throw new ExceptionInInitializerError("Empty Card List");

        completeFive = new <Card>ArrayList(secondPairCards);
        completeFive.addAll( otherCards );
        dummy = HandIdentifier.getTwoPair(firstPairCards, completeFive);
        if( dummy == null || dummy.size() < 4 )
            throw new ExceptionInInitializerError("Something terribly went wrong, please check getTwoPair()");
        dummy.clear();
    } // end constructor

    @Override
    public int compareTo(Hand anotherHand) {
      if( this.getRankInOrdinalOrder() < anotherHand.getRankInOrdinalOrder() )
        return -1;
      else
      if( this.getRankInOrdinalOrder() > anotherHand.getRankInOrdinalOrder() )
        return 1;
      else{
        // Both hands' first pairs are of equal rank. Compare second.

        if( anotherHand instanceof TwoPair ){
            TwoPair compareMe = (TwoPair) anotherHand;
            if( this.getSecondPairCards().get(0).getRankInOrdinalOrder() <
              compareMe.getSecondPairCards().get(0).getRankInOrdinalOrder() ) {
              return -1;
            }else
            if( this.getSecondPairCards().get(0).getRankInOrdinalOrder() >
              compareMe.getSecondPairCards().get(0).getRankInOrdinalOrder() ) {
                return 1;
            }

            // Still same, with the two top cards. Next, check the highcards.
            int z = -2;

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
            // if reached here, exact same ranks.
            return 0;
        }

        return 0;
      }
    } // end method compareTo

    public HandType getHandType() {
        return HandType.TWO_PAIR;
    }

    public List<Card> getOtherCards(){
        return this.otherCards;
    }

    public List<Card> getSecondPairCards(){
      return this.secondPairCards;
    }

    public CardRank getTopRank(){
        return firstPairCards.get(0).getRank();
    }

    /**
     * @return The name of the hand with kicker ranked in descending order, e.g. Two Pair (4,3) - A High
     */
    @Override
    public String toString() {
        int y = otherCards.size() - 1;
        return String.format(
                "Two Pair (%s,%s) - %s High",
                getTopRank(),
                secondPairCards.get(0).getRank(),
                otherCards.get(0).getRank()
        );
    } // end method toString()

} // end class TwoPair
