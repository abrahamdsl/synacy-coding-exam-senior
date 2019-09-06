package com.synacy.poker.hand;

import com.synacy.poker.card.CardRank;
/**
 * The base class of the different Hands such as {@link com.synacy.poker.hand.types.Flush},
 * {@link com.synacy.poker.hand.types.FullHouse}, etc.
 */
public abstract class Hand {
    private String this_version = "v0.6.0_main_d20190906-2200";
    // @changelog : new (abstract) methods:
    //   getTopRank(), compareTo(Hand), getRankInOrdinalOrder()

    /**
     * @return The {@link HandType}
     */
    public abstract HandType getHandType();

    public abstract CardRank getTopRank();

  public int compareTo(Hand anotherHand){
    if( this.getRankInOrdinalOrder() == anotherHand.getRankInOrdinalOrder() ){
      // check for ties
      return 0;
    }else{
      if( this.getRankInOrdinalOrder() < anotherHand.getRankInOrdinalOrder() )
        return -1;
      else
        return 1;
    }

  }

  // Gets rank in ordinal order - as how it's aligned ordinarily in the code.
  // @todo : Ace can be used as one or ten depending on the category.
  public int getRankInOrdinalOrder(){
    return getHandType().ordinal();
  } // end method getRankInOrdinalOrder

} // end class Hand
