package com.synacy.poker.hand;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.types.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.sun.org.apache.bcel.internal.Repository.instanceOf;
import static com.synacy.poker.card.CardSuit.*;


/**
 * A service that is to used to identify the {@link Hand} given the player's cards and the community
 * cards.
 */
/*
  Changelog:
   in identifyHand(), can now identify:
     High Card, One Pair, Two Pair, Three of a Kind, Straight

     ..and created respective methods if any

   added methods:
     mergeCards
     getOrderedCards
     getPairwiseCards
*/
@Component
public class HandIdentifier {
    private String this_version = "v0.2.0_main_d20190823-2358";
    // @changelog : added 'end class'

    /**
     * Given the player's cards and the community cards, try to identify if
     *   there's a high card amongst the cards.
     *
     * @param playerCards
     * @param communityCards
     * @return The list of the player's top five highest {@link Card}s.
     */
    private List<Card> getHighCard( List<Card> playerCards,
      List<Card> communityCards)
    {
      List<Card> consideredCards = getOrderedCards( mergeCards( playerCards, communityCards ) );
      List<Card> master = new ArrayList<Card>();

      // OLD ALGORITHM
      /*
       * Get first element of the combined cards and iterate all over to know
       *  which is the highest.
       */
      /*
      Card highCard;

      highCard = consideredCards.get(0);
      for( int i = 1, j = consideredCards.size(); i<j; i++ ) {
        Card inProcess = consideredCards.get(i);
        if( inProcess.getRankInOrdinalOrder() >
          highCard.getRankInOrdinalOrder()
        ) {
          highCard = inProcess;
        }
      }
      return highCard;
      */

      // Get the highest 5, the last should be the highest
      int y = consideredCards.size();
      int x = y;
      x--;
      x-=4;
      for( ; x<y; x++ ){
          master.add( consideredCards.get(x) );
      }

      return master;
    } // end method getHighCard

    /**
     * Given the player's cards and the community cards, try to identify if
     *   there's a one pair pattern.
     *
     * @param playerCards
     * @param communityCards
     * @return List of highest one-pair of card, or null.
     */
    private List<Card> getOnePair( List<Card> playerCards,
      List<Card> communityCards)
    {
      List<Card> consideredCards = mergeCards( playerCards, communityCards );
      List<Card> pairs = getPairwiseCards( 2, consideredCards );

      switch( pairs.size() ) {
        case 0: return null;
        case 2: return pairs;
        default:
          // @comment You really won't reach this if getTwoPair (or better)
          //   was called earlier in the originating function.
          List<Card> master = new ArrayList<Card>();

          // @ todo: Looks like ripe for a do-while loop instead, or subject
          //   to a method which orders by descending order?
          master.add( pairs.get(0) );
          master.add( pairs.get(1) );
          for(int i = 2, j = pairs.size(); i<j; i+=2 ) {
            Card examine = pairs.get(i);

            if( examine.getRankInOrdinalOrder() >
              master.get(0).getRankInOrdinalOrder()
            ) {
              master.clear();
              master.add( pairs.get(i) );
              master.add( pairs.get(1+1) );
            }
          }

          return master;
      }
    } // end method getOnePair

    /**
     * Sorts a list of cards by rank.
     *
     * @param toBeSorted
     * @param List of sorted {@link Card}s.
     */
    private List<Card> getOrderedCards( List<Card> toBeSorted ) {
      Collections.sort(
        toBeSorted,
        new Comparator<Card>()
          {
            public int compare(Card first, Card second) {
              return (
                first.getRankInOrdinalOrder() < second.getRankInOrdinalOrder()
              ) ? -1 : 1;
            }
          }
      );
      return toBeSorted;
    } // end method getOrderedCards

     /**
     * Given the player's cards and the community cards, try to identify if
     *   there's a straight pattern.
     *
     * @param playerCards
     * @param communityCards
     * @return List of three of {@link Card}s of the same ranks, or null
     *   if this pattern isn't present.
     */
    // @todo : Can ace be used both ways ( A-2-3-4-5 or 6-7-8-9-A ) ?
    private List<Card> getStraight( List<Card> playerCards,
      List<Card> communityCards, boolean sameSuit)
    {
      List<Card> orderedList = new <Card>ArrayList();
      List<Card> master = new <Card>ArrayList();
      Card previous = null;
      int count = 0;

      if( sameSuit ){
       // assumption: getFlush() always returns ordered cards
        orderedList = getFlush(playerCards, communityCards);
        if( orderedList == null )
            return null;
      }else {
          orderedList = getOrderedCards(
                  mergeCards(playerCards, communityCards)
          );
      }

      // @todo : Does the compiler optimize : about int = 1, j = list.size()...?
      int y = orderedList.size();
      int x = y - 1;
      x -= 4;
      previous = orderedList.get(x);
      x++;
      for( ; x<y; x++ ) {
         Card examine = orderedList.get(x);
        //if( previous != null ) {
          if(
              examine.getRankInOrdinalOrder() - previous.getRankInOrdinalOrder()
              == 1
          ){
            master.add( previous );
            count++;
            // only five needed
            if( count == 4 )  {
              master.add( examine );
              break;
            }
          }else{
            master.clear();
            break;
          }
        //}
        previous = examine;
      }

      //throw new Exception("This should not be reached?");
        return master.size() == 5 ? master : null;
    } // end method getStraight

    /**
     * Given the player's cards and the community cards, try to identify if
     *   there's a three-of-a-kind pattern.
     *
     * @param playerCards
     * @param communityCards
     * @return List of three of {@link Card}s of the same ranks, or null
     *   if this pattern isn't present.
     */
    private List<Card> getThreeOfAKind( List<Card> playerCards,
      List<Card> communityCards)
    {
      List<Card> consideredCards = mergeCards( playerCards, communityCards );
      List<Card> pairs = getPairwiseCards( 3, consideredCards );

      switch( pairs.size() ) {
        case 3: return pairs;
        default:
          // @todo: review if possible
          return null;
      }
    } // end method getThreeOfAKind

    /**
     * Given the player's cards and the community cards, try to identify if
     *   there's a two pair pattern.
     *
     * @param playerCards
     * @param communityCards
     * @return List of two pairs of {@link Card}s of the same ranks, or null
     *   if this pattern isn't present.
     */
    private List<Card> getTwoPair( List<Card> playerCards,
      List<Card> communityCards)
    {
      List<Card> consideredCards = mergeCards( playerCards, communityCards );
      List<Card> pairs = getPairwiseCards( 2, consideredCards );

      switch( pairs.size() ) {
        case 4: return pairs;
        default:
          // @todo: review if possible
          return null;
      }
    } // end method getTwoPair

    /**
     * Check the list of player's cards for n-number of pairs.
     *
     * @param pairQuantity
     * @param mergedCards
     * @return List of pairs of {@link Card}s, or empty List if nothing found
     */
    public static List<Card> getPairwiseCards(
      Integer pairQuantity,
      List<Card> mergedCards
    ) {
      List<Card> pairwise = new <Card>ArrayList();
      List<Card> master = new <Card>ArrayList();
      //List<int> skips = new <int>ArrayList();

      // @todo: What better algorithm could be rather than O(n^2) ?
      //   - perhaps, iterate and determine ranks first. Put them in a set.
      //    if size equals bla bla.. not okay.
      // @todo: What if two pairs, separate here, or in the other function?
      for( Card firstCard : mergedCards ){
        // already chosen as part of a pair, so irrelevant now
        if( master.size() != 0 && master.contains(firstCard) )
          continue;

        pairwise.add( firstCard );
        for( Card secondCard: mergedCards ){
          // skip if same card
          if( firstCard.equals( secondCard ) )
            continue;

          // already chosen as part of a pair, so irrelevant now
          // @todo : is this really needed ?
          if( master.size() != 0 && master.contains(secondCard) )
            continue;

          // if same rank, add then
          if( firstCard.getRankInOrdinalOrder() ==
            secondCard.getRankInOrdinalOrder()
          ) {
            pairwise.add( secondCard );
          }
        } // end 'for each' loop -secondCard

        // we have now reached the required number to search, so return now
        // @todo: So in originating function, looks like three pairs must be
        //   called first, before two pairs, or use modulus to this return's
        //   List if not null
        if( pairwise.size() != 0  && ( pairwise.size() % pairQuantity ) == 0 )
          master.addAll(pairwise);

        pairwise.clear();
      } // end 'for each' loop -firstCard

      return master;
    } // end method ( getPairwiseCards )

    /**
     * Given the player's cards and the community cards, identifies the player's hand.
     *
     * @param playerCards
     * @param communityCards
     * @return The player's {@link Hand} or `null` if no Hand was identified.
     */
    public Hand identifyHand(List<Card> playerCards, List<Card> communityCards) {
      Hand theHand = null;
      List<Card> candidateCards = new ArrayList<Card>();
      // @comment adsllave here lah //:checkRanking(..) .. check which , then create a pre-supplied class based on it

      // Check if 'Royal Flush'

      // Check if 'Straight Flush'

      // Check if 'Four of a Kind'

      // Check if 'Full House'

      // Check if 'Flush'

      // Check if 'Straight'
        if( theHand == null ) {
            candidateCards = getStraight(playerCards, communityCards, false);

            if( ! ( candidateCards == null || candidateCards.size() == 0 ) ) {
                theHand = new Straight(candidateCards);
            }
        }

      // Check if 'Three of a Kind'
       if( theHand == null ) {
            candidateCards = getThreeOfAKind(playerCards, communityCards);

            if( ! ( candidateCards == null || candidateCards.size() == 0 ) ) {
                List<Card> otherCards = mergeCards(playerCards, communityCards);
                otherCards.removeAll(candidateCards);
                theHand = new ThreeOfAKind( candidateCards, getOrderedCards( otherCards ) );
            }
        }

      // Check if 'Two Pair'
        if( theHand == null ) {
            candidateCards = getTwoPair(playerCards, communityCards);

            if( ! ( candidateCards == null || candidateCards.size() == 0 ) ) {
                List<Card> otherCards = mergeCards(playerCards, communityCards);
                List<Card> secondPair = new ArrayList<Card>();

                // not in consideration for the Two Pairs
                otherCards.removeAll(candidateCards);
                otherCards = getOrderedCards(otherCards);

                // sort, returns in ascending order
                candidateCards = getOrderedCards(candidateCards);

                // lower value pair comes first in order so...
                secondPair.add( candidateCards.get(0) );
                secondPair.add( candidateCards.get(1) );
                candidateCards.removeAll(secondPair);

                theHand = new TwoPair(
                        candidateCards,
                        secondPair,
                        otherCards // @todo: might not be trimmed to 1 highest card (max of 5 in a hand right?)
                );
            }
        }

      // Check if 'One Pair'
       if( theHand == null ) {
           candidateCards = getOnePair(playerCards, communityCards);

           if( ! ( candidateCards == null || candidateCards.size() == 0 ) ) {
            List<Card> otherCards = mergeCards(playerCards, communityCards);
            otherCards.removeAll(candidateCards);
            otherCards=getOrderedCards(otherCards);
            theHand = new OnePair(candidateCards, otherCards);
           }
       }

      // Check if 'High Card'
      if( theHand == null ) {
          theHand = new HighCard( getHighCard(playerCards, communityCards) );
      }

      return theHand;
      // WRONG::::  If reached, nothing eligible for this player
      //   he always has a high card
      // candidateCards.removeAll(null);
      // if( candidateCards.size() == 0)
      //   return null;
    } // identify Hand

    /**
     *  Merge cards of the player and that of the table's,to be returned as a
     *    list (too).
     *
     * @param playerCards
     * @param communityCards
     * @return The List of {@link Card}s.
     */
    public static List<Card> mergeCards(
      List<Card> playerCards, List<Card> communityCards
    ) {
      List<Card> allCards = new <Card>ArrayList();

      allCards.addAll( playerCards );
      allCards.addAll( communityCards );

      return allCards;
    } // end method mergeCards

} // end class HandIdentifier
