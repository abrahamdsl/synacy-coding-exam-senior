package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Full_house">What is a Full House?</a>
 */
// @changelog: new method compareTo(Hand), getSecondPairCards(), getTopRank(), utilisation of latter in .toString()
//   throw exception if inappropriate construction encountered.
public class FullHouse extends Hand {
	private String this_version = "v0.6.0_main_d20190906-2200";

    private List<Card> threeOfAKindCards;
    private List<Card> pairCards;

    public FullHouse(List<Card> threeOfAKindCards, List<Card> pairCards) {
        this.threeOfAKindCards = threeOfAKindCards;
        this.pairCards = pairCards;

        List<Card> dummy = HandIdentifier.getFullHouse( threeOfAKindCards, pairCards );

        if( dummy == null || dummy.size() < 5 )
            throw new ExceptionInInitializerError("Something terribly went wrong, please check getFullHouse()");
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

        if( anotherHand instanceof FullHouse ){
            FullHouse compareMe = (FullHouse) anotherHand;
            if( this.getSecondPairCards().get(0).getRankInOrdinalOrder() <
              compareMe.getSecondPairCards().get(0).getRankInOrdinalOrder() ) {
              return -1;
            }else
            if( this.getSecondPairCards().get(0).getRankInOrdinalOrder() >
              compareMe.getSecondPairCards().get(0).getRankInOrdinalOrder() ) {
                return 1;
            }

            // if reached here, exact same ranks.
            return 0;
        }

        return 0;
      }
    } // end method compareTo

    public HandType getHandType() {
        return HandType.FULL_HOUSE;
    }

    public List<Card> getSecondPairCards(){
      return this.pairCards;
    }

    public CardRank getTopRank(){
        return threeOfAKindCards.get(0).getRank();
    }

    /**
     * @return The name of the hand with rank of the three pair and two pair, e.g.
     * 444AA - Full House (4,A)
     */
    @Override
    public String toString() {
        return String.format(
                "Full House (%s,%s)",
                getTopRank(),
                pairCards.get(0).getRank()
        );
    }

} // end class FullHouse
