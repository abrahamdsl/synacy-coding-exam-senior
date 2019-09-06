package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.HandType;

import java.util.Collections;
import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#One_pair">What is a One Pair?</a>
 */
// @changelog: new method compareTo(Hand), getOtherCards(), getTopRank(), utilisation of latter in .toString(),
//   throw exceptions if inappropriate construction encountered.
public class OnePair extends Hand {
	private String this_version = "v0.6.0_main_d20190906-2200";

    private List<Card> pairCards;
    private List<Card> otherCards;

    public OnePair(List<Card> pairCards, List<Card> otherCards)
    throws ExceptionInInitializerError {
        this.pairCards = pairCards;
        this.otherCards = otherCards;

        int sizeTotal = 0;

        if( this.pairCards == null )
            throw new ExceptionInInitializerError("Null Card List");
        if( this.pairCards.size() == 0 )
            throw new ExceptionInInitializerError("Empty Card List");

        sizeTotal = this.pairCards.size();
        if( this.otherCards != null )
          sizeTotal += this.otherCards.size();
        this.pairCards = HandIdentifier.getOnePair(
          pairCards,
          otherCards == null ? Collections.emptyList() : otherCards,
          false
        );
        if( this.pairCards == null){
            String val = "";
            for( Card c: pairCards ) { val += String.format("[d] pairCards %s\n", c ); }
            for( Card c: otherCards ) { val += String.format("[d] pairCards%s\n", c ); }
            throw new ExceptionInInitializerError("Something terribly went wrong, please check getOnePair()" + val );
        }
    } // end constructor

    // By-card of getOtherCards() still doesn't return all if tie.
    //  e.g. (A,4;10,K;A,5)(3,7,9,J,J)
    @Override
    public int compareTo(Hand anotherHand){
      // first compare hand types
      if( this.getRankInOrdinalOrder() < anotherHand.getRankInOrdinalOrder() )
        return -1;
      else
      if( this.getRankInOrdinalOrder() > anotherHand.getRankInOrdinalOrder() )
        return 1;
      else{
        // Now, same hand type, compare the defining logic (e.g. highest rank
        //  for a OnePair like in a OnePair(K) vs OnePair(Q), the earlier wins.
        // @todo: Do further internal logic for tie breaking.
        if( this.getTopRank().ordinal() < anotherHand.getTopRank().ordinal() )
          return -1;
        else
        if( this.getTopRank().ordinal() > anotherHand.getTopRank().ordinal() )
          return 1;
        else{
          // Still same, with the top rank. Next, check the highcards.

          int z = -2;

          if( anotherHand instanceof OnePair ){
            OnePair compareMe = (OnePair) anotherHand;
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
        return HandType.ONE_PAIR;
    }

    public List<Card> getOtherCards(){
        return this.otherCards;
    }

    public CardRank getTopRank(){
        return pairCards.get(0).getRank();
    }

    /**
     * @return The name of the hand plus kickers ordered by descending rank, e.g. One Pair (2) - A,K,Q High,
     * or the name of the hand and rank if there are no community cards yet in play, e.g. One Pair (2)
     */
    @Override
    public String toString(){
        String ret = String.format( "One Pair (%s)", this.getTopRank() );
        int s = -1;

        if( otherCards != null ){
          s = otherCards.size();
          if( s == 3 ) {
            ret += String.format(
              " - %s,%s,%s High",
              otherCards.get(s-3).getRank().toString(),
              otherCards.get(s-2).getRank().toString(),
              otherCards.get(s-1).getRank().toString()
            );
          }
        }

        return ret;
    } // end method toString

} // end class OnePair
