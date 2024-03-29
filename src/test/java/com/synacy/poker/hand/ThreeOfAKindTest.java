package com.synacy.poker.hand;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.card.CardSuit;
import com.synacy.poker.hand.types.ThreeOfAKind;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ThreeOfAKindTest {
    private String this_version = "v0.1.0_main_d20190822-2358";
    // @changelog : Just added 'end class' indicator

    @Test
    public void toString_withTripFoursAndKickers() {
        List<Card> trips = Arrays.asList(
                new Card(CardRank.FOUR, CardSuit.CLUBS),
                new Card(CardRank.FOUR, CardSuit.DIAMONDS),
                new Card(CardRank.FOUR, CardSuit.SPADES)
        );
        List<Card> kickers = Arrays.asList(
                new Card(CardRank.ACE, CardSuit.CLUBS),
                new Card(CardRank.TWO, CardSuit.CLUBS)
        );

        ThreeOfAKind threeOfAKind = new ThreeOfAKind(trips, kickers);

        assertEquals("Trips (4) - A,2 High", threeOfAKind.toString());
    }

} // end classs ThreeOfAKindTest
