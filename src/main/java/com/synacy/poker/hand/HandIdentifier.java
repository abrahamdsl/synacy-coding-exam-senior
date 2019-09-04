package com.synacy.poker.hand;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.hand.types.*;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.synacy.poker.card.CardSuit.*;


/**
 * A service that is to used to identify the {@link Hand} given the player's cards and the community
 * cards.
 */
// @todo: Aside from RoyalFlush, check other get functions for eliminatePossibleDuplicates
/*
  Changelog:
    @bugfix for get<HandType>() functions, checked if candidate cards
      count is at least five, else return null.
    Always call getHighCard if search for higher hands returned null.
    Minor grammatical changes in comments
    re-coding like indents and spacing and extending column limit to
      100 because IntelliJ IDEA defaults to that anyway.
    Removed obsoleted/commented-out code
*/
@Component
public class HandIdentifier {
    private String this_version = "v0.5.3_main_d20190904-2330";

    private static final int CANDIDATE_COUNT = 5;

    public static List<Card> eliminatePossibleDuplicates(List<Card> set1, List<Card> set2){
        List<Card> consideredCards = mergeCards(set1, set2);
        List<Card> dummyCardList = new <Card>ArrayList();
        List<Card> possibleRankDuplicates = getOnePair(consideredCards, dummyCardList);

        while( possibleRankDuplicates != null ){
            // there's always at least 2 elements, if not null
            consideredCards.remove( possibleRankDuplicates.get(1) );

            possibleRankDuplicates = getOnePair( consideredCards, dummyCardList );
        }
        return consideredCards;
    }  // end method eliminatePossibleDuplicates

    /**
     * Given the player's cards and the community cards, try to identify if
     *   there's a 'Flush Pattern'.
     *
     * At least five cards of the same pattern/suit should be present.
     *
     * @param playerCards
     * @param communityCards
     * @return The list of the {@link Card}s with same suit.
     */
    // @todo : Change the algorithm to maybe use something relating to Perl's
    //   hashes, to reduce variables and if statements.
    // @todo : How about throwing exception , because cannot determine suit?
    public static List<Card> getFlush( List<Card> playerCards, List<Card> communityCards) {
        List<Card> consideredCards = getOrderedCards( mergeCards( playerCards, communityCards ) );

        List<Card> clubs = new ArrayList<Card>();
        List<Card> diamonds = new ArrayList<Card>();
        List<Card> hearts = new ArrayList<Card>();
        List<Card> spades = new ArrayList<Card>();

        for( Card examine: consideredCards ){
            switch( examine.getSuit() ) {
                case DIAMONDS :
                    diamonds.add( examine );
                    break;
                case HEARTS :
                    hearts.add( examine );
                    break;
                case CLUBS :
                    clubs.add( examine );
                    break;
                case SPADES :
                    spades.add( examine );
                    break;
            }
        }

        if( clubs.size() >= CANDIDATE_COUNT )
            return clubs;
        if( diamonds.size() >= CANDIDATE_COUNT )
            return diamonds;
        if( hearts.size() >= CANDIDATE_COUNT )
            return hearts;
        if( spades.size() >= CANDIDATE_COUNT )
            return spades;

        return null;
    }// end method getFlush

    /**
     * Given the player's cards and the community cards, try to identify if
     *   there's a 'Four of a Kind' pattern.
     *
     * @param playerCards
     * @param communityCards
     * @return List of two pairs of {@link Card}s of the same ranks, or null
     *   if this pattern isn't present.
     */
    private List<Card> getFourOfAKind( List<Card> playerCards, List<Card> communityCards ) {
        List<Card> consideredCards = mergeCards( playerCards, communityCards );
        List<Card> pairs;

        // means total number of cards submitted to this function is less than 5
        if( consideredCards.size() < CANDIDATE_COUNT )
            return null;

        pairs = getPairwiseCards( 4, consideredCards );

        // todo : Possible conversion to simple if statement
        switch( pairs.size() ) {
            case 4: return pairs;
            default:
                // @todo: review if possible
                return null;
        }
    } // end method getFourOfAKind

    /**
     * Given the player's cards and the community cards, try to identify if
     *   there's a 'Full House' pattern.
     *
     * @param playerCards
     * @param communityCards
     * @return List of two pairs of {@link Card}s of the same ranks, or null
     *   if this pattern isn't present.
     */
    private List<Card> getFullHouse( List<Card> playerCards, List<Card> communityCards ) {
        List<Card> master = null;
        List<Card> temp = new <Card>ArrayList();
        List<Card> threePair;

        // means total number of cards submitted to this function is less than 5
        if( playerCards.size() + communityCards.size() < CANDIDATE_COUNT )
            return null;

        threePair = getThreeOfAKind(playerCards, communityCards);
        if( threePair != null ){
            List<Card> twoPair;
            int x = 0;

            temp = mergeCards(playerCards, communityCards);

            temp.removeAll(threePair);
            twoPair = getOnePair(new <Card>ArrayList(),temp,false);

            if( twoPair == null)
                return null;

            x = twoPair.size();
            if(  x > 0 ) {
                master = new <Card>ArrayList();

                master.addAll(threePair);
                master.add( twoPair.get(x-2) );
                master.add( twoPair.get(x-1) );
            }
        }

        return master;
    } // end method getFullHouse

    /**
     * Given the player's cards and the community cards, try to identify if
     *   there's a high card amongst the cards.
     *
     * @param playerCards
     * @param communityCards
     * @return The list of the player's top five highest {@link Card}s.
     */
    public static List<Card> getHighCard( List<Card> playerCards, List<Card> communityCards) {
      List<Card> consideredCards = getOrderedCards( mergeCards( playerCards, communityCards ) );
      List<Card> master = new ArrayList<Card>();

      // Get the highest 5, the last should be the highest
      int y = consideredCards.size();
      int x = ( y - 1 ) - 4;

      // means total number of cards submitted to this function is less than 5
      if( x < 0 )
          return null;

       master = consideredCards.subList( x, y );
       Collections.reverse( master );

      return master;
    } // end method getHighCard

    /**
     * @see {@link this.getOnePair(List<Card>, List<Card>, boolean)}
     */
    public static List<Card> getOnePair( List<Card> playerCards, List<Card> communityCards ) {
        return getOnePair( playerCards, communityCards, true );
    } //end method getOnePair(3)

    /**
     * Given the player's cards and the community cards, try to identify if
     *   there's a one pair pattern.
     *
     * @param playerCards
     * @param communityCards
     * @param checkMinFiveSize Signifies if we need to enforce requirement of minimum of five cards submitted to this
     *   function. For some cases, like call from {@link this.getFullHouse()} , this has to be waived.
     * @return List of highest one-pair of card, or null.
     */

    public static List<Card> getOnePair( List<Card> playerCards, List<Card> communityCards, boolean checkMinFiveSize ) {
      List<Card> consideredCards = mergeCards( playerCards, communityCards );
      List<Card> pairs = null;

      if( checkMinFiveSize && (consideredCards.size() < CANDIDATE_COUNT ) )
        return null;

      pairs = getPairwiseCards( 2, consideredCards );

      switch( pairs.size() ) {
        case 0: return null;
        case 2: return pairs;
        default:
          // @comment You really won't reach this if getTwoPair (or better)
          //   was called earlier in the originating function,
          //   ceteris paribus.
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
     * Sorts a list of cards by rank, in ascending order.
     *
     * The order of resulting list is not in what's expected in a Poker Hand.
     * Example:
     *   (2,3,4,5,A) , not (A,2,3,4,5)
     *
     * @param toBeSorted
     * @param List of sorted {@link Card}s.
     */
    public static List<Card> getOrderedCards( List<Card> toBeSorted ) {
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
     * Check the list of player's cards for n-number of pairs.
     *
     * @param pairQuantity
     * @param mergedCards
     * @return List of pairs of {@link Card}s, or empty List if nothing found
     */
    public static List<Card> getPairwiseCards( Integer pairQuantity, List<Card> mergedCards ) {
        List<Card> master = new <Card>ArrayList();
        List<Card> pairwise = new <Card>ArrayList();

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
     * Given the player's cards and the community cards, identify if
     *  there's a Royal Flush pattern
     *
     *
     * @param playerCards
     * @param communityCards
     * @return The player's {@link Hand} or `null` if no Hand was identified.
     */
    public static List<Card> getRoyalFlush(List<Card> playerCards, List<Card> communityCards) {
        List<Card> consideredCards = null;
        List<Card> dummyCardList = new <Card>ArrayList();

        String comparo = "10,J,Q,K,A";
        String gotten = "";

        try {
            consideredCards = getStraight(
                    eliminatePossibleDuplicates(playerCards, communityCards),
                    dummyCardList,
                    false
            );
        }catch(Exception e){
            e.printStackTrace();
        }

        // not even a straight pattern so no point checking the card ranks
        if( consideredCards == null)
            return null;

        gotten = String.format(
                "%s,%s,%s,%s,%s",
                consideredCards.get(0).getRank(),
                consideredCards.get(1).getRank(),
                consideredCards.get(2).getRank(),
                consideredCards.get(3).getRank(),
                consideredCards.get(4).getRank()
        );

        return ( comparo.equalsIgnoreCase( gotten ) ) ? consideredCards : null;
        //return master;
    } // end method getRoyalFlush

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
    public static List<Card> getStraight( List<Card> playerCards, List<Card> communityCards, boolean sameSuit)
        throws Exception
    {
      List<Card> orderedList = new <Card>ArrayList();
      List<Card> master = new <Card>ArrayList();
      Card previous = null;
      int count = 0;
      int acePosition = -1;
      int orderedList_size = 0;
      int orderedListLastIndex = 0;
      int x = 0;

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

      // at least five cards should be submitted to this function
      if( orderedList.size() < CANDIDATE_COUNT )
          return null;

      // As of making, master is empty - check always if making changes.
      orderedList = eliminatePossibleDuplicates(orderedList, master);

      // @todo : Does the compiler optimize : about int = 1, j = list.size()...?
      // Reminder: orderedList_size is after the pre-processing done
      orderedList_size = orderedList.size();
      orderedListLastIndex = orderedList_size - 1 ;
      x = orderedListLastIndex;

      // start of element up to five
      x -= 4;

      // Meaning, remaining eligible cards are now less than 5. Could be
      //   five at least but remember, we eliminated the duplicates.
      if( x < 0 )
          return null;

      // check for ace - by default it's in the top always
      if( orderedList.get(orderedListLastIndex).getRank().compareTo( CardRank.ACE ) == 0){
          acePosition = orderedListLastIndex;
      }

      // meaning ace is present
      if( acePosition > - 1 ){
          // check first if it's the normal elite cards
          String comparo = "";
          String cardPattern = "";

          comparo = String.format(
                  "%s,%s,%s,%s,%s",
                  CardRank.TEN,
                  CardRank.JACK,
                  CardRank.QUEEN,
                  CardRank.KING,
                  CardRank.ACE
          );
          cardPattern = String.format(
                  "%s,%s,%s,%s,%s",
                  orderedList.get(x).getRank(),
                  orderedList.get(x+1).getRank(),
                  orderedList.get(x+2).getRank(),
                  orderedList.get(x+3).getRank(),
                  orderedList.get(x+4).getRank()
          );
          if( cardPattern.equalsIgnoreCase(comparo) ){
              // meaning, the top cards are 10,J,Q,K,A
              master = orderedList.subList(x,orderedList_size);
          }else{
              comparo = String.format(
                      "%s,%s,%s,%s",
                      CardRank.TWO,
                      CardRank.THREE,
                      CardRank.FOUR,
                      CardRank.FIVE
              );
              cardPattern = String.format(
                      "%s,%s,%s,%s",
                      orderedList.get(x).getRank(),
                      orderedList.get(x+1).getRank(),
                      orderedList.get(x+2).getRank(),
                      orderedList.get(x+3).getRank()
              );
              if( cardPattern.equalsIgnoreCase(comparo) ){
                // meaning, cards originally are in 2,3,4,5,A

                // add the ace first
                master.add( orderedList.get( orderedListLastIndex ) );
                master.addAll( orderedList.subList( x, orderedListLastIndex ) );
              }
          }
      }else{
          // No ace present

          previous = orderedList.get(x);
          for( x++ ; x < orderedList_size; x++ ) {
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
      } // end if-else, no ace present

       return master.size() == CANDIDATE_COUNT ? master : null;
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
    // @todo : Switch to public static
    private List<Card> getThreeOfAKind( List<Card> playerCards, List<Card> communityCards ) {
      List<Card> consideredCards = mergeCards( playerCards, communityCards );
      List<Card> pairs = getPairwiseCards( 3, consideredCards );

      // @todo: Switch to if-statement
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
    // @todo : Switch to public static
    private List<Card> getTwoPair( List<Card> playerCards, List<Card> communityCards ) {
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
     * Given the player's cards and the community cards, identifies the player's hand.
     *
     * @param playerCards
     * @param communityCards
     * @return The player's {@link Hand} or `null` if no Hand was identified.
     */
    public Hand identifyHand( List<Card> playerCards, List<Card> communityCards ) {
        List<Card> candidateCards = new <Card>ArrayList();
        Hand theHand = null;

        // Check if 'Royal Flush'
        candidateCards = getRoyalFlush(playerCards, communityCards);

        if (candidateCards != null) {
            theHand = new RoyalFlush(
                candidateCards,
                candidateCards == null? "null" : "size: " + candidateCards.size()
            );
        }

        // Check if 'Straight Flush'
        if( theHand == null ) {
            try {
                candidateCards = getStraight(playerCards, communityCards, true);
            }catch(Exception e){
                e.printStackTrace();
            }
            if (candidateCards != null) {
                theHand = new StraightFlush(candidateCards);
            }
        }

        // Check if 'Four of a Kind'
        if( theHand == null ) {
            candidateCards = getFourOfAKind(playerCards, communityCards);

            if( candidateCards != null ) {
                List<Card> otherCards = getOrderedCards(
                        mergeCards(playerCards, communityCards)
                );
                otherCards.removeAll(candidateCards);
                theHand = new FourOfAKind(candidateCards, otherCards);
            }
        }

        // Check if 'Full House'
        if( theHand == null ) {
            candidateCards = getFullHouse(playerCards, communityCards);

            if( candidateCards != null )
                theHand = new FullHouse(
                        candidateCards.subList(0,4),
                        candidateCards.subList(3,5)
                );
        }

        // Check if 'Flush'
        if( theHand == null ) {
            candidateCards = getFlush(playerCards, communityCards);

            if( candidateCards != null )
                theHand = new Flush(candidateCards);
        }

        // Check if 'Straight'
        if( theHand == null ) {
            try {
                candidateCards = getStraight(playerCards, communityCards, false);
            }catch(Exception e){
                e.printStackTrace();
            }
            if( ! ( candidateCards == null || candidateCards.size() == 0 ) ) {
                theHand = new Straight(candidateCards, "Straight", false);
            }
        }

        // Check if 'Three of a Kind'
       if( theHand == null ) {
            candidateCards = getThreeOfAKind(playerCards, communityCards);

            if( ! ( candidateCards == null || candidateCards.size() == 0 ) ) {
                List<Card> otherCards = mergeCards(playerCards, communityCards);
                otherCards.removeAll(candidateCards);
                getOrderedCards( otherCards );
                otherCards.subList(0,2).clear();
                Collections.reverse(otherCards);
                theHand = new ThreeOfAKind( candidateCards, otherCards );
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
            try{
              // @todo: more tests for this.
              otherCards.subList(0,2).clear();            // remove lowest two
            }catch(IndexOutOfBoundsException e){
              String debugMsg = String.format(
                      "IndexOutOfBoundsException in OnePair Attempt, candidates: %d, others: %d\n",
                      candidateCards.size(),
                      otherCards.size()
               );
               for( Card examine: candidateCards) {
                 debugMsg += String.format( "\tCandidates:%s\n", examine );
               }
               for( Card examine: otherCards) {
                 debugMsg += String.format( "\tCardFail:%s\n", examine );
               }
               throw new IndexOutOfBoundsException(debugMsg);
             } // end catch

              Collections.reverse(otherCards);
              theHand = new OnePair(candidateCards, otherCards);
            }
       }

      // Check if 'High Card'
      if( theHand == null ) {
          candidateCards = getHighCard(playerCards, communityCards);

          if( candidateCards != null )
            theHand = new HighCard( candidateCards );
      }

      return theHand;
    } // identify Hand

    /**
     * Merge two lists of cards to be returned as one new list.
     *
     * No sorting or duplicate removal to occur.
     *
     * @param set1
     * @param set2
     * @return The List of {@link Card}s : set1 appended with set2
     */
    public static List<Card> mergeCards( List<Card> set1, List<Card> set2 ) {
      List<Card> allCards = new <Card>ArrayList();

      allCards.addAll( set1 );
      allCards.addAll( set2 );

      return allCards;
    } // end method mergeCards

} // end class HandIdentifier
