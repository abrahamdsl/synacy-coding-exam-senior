package com.synacy.poker.hand;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.card.CardSuit;
import com.synacy.poker.hand.types.FourOfAKind;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FourOfAKindTest {
    private String this_version = "v0.1.0_main_d20190822-2358";
    // @changelog : Just added 'end class' indicator

    @Test
    public void toString_withQuadFoursAndAceKicker() {
        List<Card> quads = Arrays.asList(
                new Card(CardRank.FOUR, CardSuit.CLUBS),
                new Card(CardRank.FOUR, CardSuit.DIAMONDS),
                new Card(CardRank.FOUR, CardSuit.SPADES),
                new Card(CardRank.FOUR, CardSuit.HEARTS)
        );
        List<Card> kicker = Collections.singletonList(
                new Card(CardRank.ACE, CardSuit.CLUBS)
        );

        FourOfAKind fourOfAKind = new FourOfAKind(quads, kicker);

        assertEquals("Quads (4) - A High", fourOfAKind.toString());
    }

} // end class FourOfAKindTest
