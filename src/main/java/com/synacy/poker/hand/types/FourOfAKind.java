package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Four_of_a_kind">What is a Four of a Kind?</a>
 */
// @changelog: new method compareTo(Hand), getOtherCards(), getTopRank(), utilisation of latter in .toString()
//   throw exception if inappropriate construction encountered.
public class FourOfAKind extends Hand {
	private String this_version = "v0.6.0_main_d20190906-2200";

    private List<Card> fourOfAKindCards;
    private List<Card> otherCards;

    public FourOfAKind(List<Card> fourOfAKindCards, List<Card> otherCards) {
      this.fourOfAKindCards = fourOfAKindCards;
      this.otherCards = otherCards;

      List<Card> dummy = HandIdentifier.getFourOfAKind( fourOfAKindCards, otherCards );

      if( dummy == null || dummy.size() < 4 )
          throw new ExceptionInInitializerError("Something terribly went wrong, please check getFourOfAKind()");
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

          if( anotherHand instanceof FourOfAKind ){
            FourOfAKind compareMe = (FourOfAKind) anotherHand;
            // iterate over the otherCards to see
            if( getOtherCards().size() != compareMe.getOtherCards().size() ){
              System.err.println("Non matching sizes");
            }else{
              z = getOtherCards().size();
            }

            // todo: perhaps reduce to just one, as we only have 5 cards right?
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
        return HandType.FOUR_OF_A_KIND;
    }

    public List<Card> getOtherCards(){
        return this.otherCards;
    }

    public CardRank getTopRank(){
        return fourOfAKindCards.get(0).getRank();
    }

    /**
     * @return Returns the name of the hand plus kicker, e.g. Quads (4) - A High
     */
    @Override
    public String toString() {
        return String.format(
                "Quads (%s) - %s High",
                getTopRank(),
                otherCards.get( otherCards.size() - 1).getRank()
        );
    }

} // end class FourOfAKind
