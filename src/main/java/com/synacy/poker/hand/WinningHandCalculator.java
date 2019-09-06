package com.synacy.poker.hand;

import org.springframework.stereotype.Component;

// @todo: Restore to just necessary libraries as of v0.1.0_main_d20190822-2358
//   plus the difference.
import java.util.*;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.HandType;

/**
 * A service class used to calculate the winning hand.
 */
// @todo : Make indent consistent across project (maybe Drupal 2-spaces)?
@Component
public class WinningHandCalculator {
       private String this_version = "v0.6.0_main_d20190906-2200";
       // @changelog : filled-in calculateWinningHand(), new method getOrderedHands(List<Hand>), converted indent to
       //   2-spaces

	/**
	 * @param playerHands
	 * @return The winning {@link Hand} from a list of player hands.
	 */
	public Optional<Hand> calculateWinningHand(List<Hand> playerHands) {
        // @comment adsllave here lah
        List<Hand> sortedHands = getOrderedHands( playerHands );
        Optional<Hand> optie = Optional.empty();

        int z = sortedHands.size();
        int y = z - 1;
        for( Hand unsorted :playerHands ){
          System.out.printf( "unsorted: %s\n", unsorted );
        }
        for( Hand sorted :sortedHands ){
          System.out.printf( "Sorted: %s\n", sorted );
        }

        if( z == 1 )
          optie = Optional.of( sortedHands.get(0) );
        else{
          if( sortedHands.get(y).getRankInOrdinalOrder() >  sortedHands.get(y-1).getRankInOrdinalOrder() )
            optie = Optional.of( sortedHands.get(y) );
          else{
            // only case by now is equal, since call to getOrderedHands returns ordered list
            int x = -1;

            for( x = y;
                 x >=0 && sortedHands.get(x).getRankInOrdinalOrder() == sortedHands.get(y).getRankInOrdinalOrder();
                 x--
            ) {
              // do nothing
            }
            // x will contain lower one
            if( y == x ) // means last in the list is the highest - no tie at all
              optie = Optional.of( sortedHands.get(y) );
            else{
              // at this point, x is the member that is lower ranked
              // this list contains Hands the are of same rank - we have to break
              //   down the tie via next lower hand part or High Cards
              List<Hand> clincher = sortedHands.subList( x+1, y+1 );

              int a = -2;
              int clincherLastIndex = clincher.size() - 1;
              Hand previous = clincher.get( clincherLastIndex );

              for( a = clincherLastIndex-1; a>-1; a-- ){
                Hand current = clincher.get( a );
                // @todo: or should be -1?
                if( current.compareTo( previous ) != 0 ){
                  optie = Optional.of(previous);
                  break;
                }else{
                  current = previous;
                }
              }

              if( a == - 1 ){
                // means all are of equal ranks
                System.out.println( "Oh, all hands are of equal ranks!\n" );
                for( Hand all_ : clincher ) {
                  System.out.println( clincher );
                }
              }

            } // end if-else(y==x)
          } // end if-else( sortedHands.get(y).getRankInOrdinalOrder() >  sortedHands.get(y-1).getRankInOrdinalOrder() )
        } // end if-else( z == 1 )

        return optie;
	} // end method calculateWinningHand

    /**
     * Sorts a list of hands by rank, in ascending order.
     *
     *
     * @param toBeSorted
     * @param List of sorted {@link Hand}s.
     */
    public static List<Hand> getOrderedHands( List<Hand> toBeSorted ) {
      List<Hand> sortedList = new <Hand>ArrayList(toBeSorted);

      Collections.sort(
              sortedList,
              new Comparator<Hand>() {
                public int compare(Hand first, Hand second) {
                  return first.compareTo( second );
                }
              }
      );
      return sortedList;
    } // end method getOrderedHands

} // end class WinningHandCalculator
