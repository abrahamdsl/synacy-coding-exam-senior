package com.synacy.poker.hand;

import com.synacy.poker.card.Card;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A service that is to used to identify the {@link Hand} given the player's cards and the community
 * cards.
 */
@Component
public class HandIdentifier {
    private String this_version = "v0.1.0_main_d20190822-2358";
    // @changelog : added 'end class'

    /**
     * Given the player's cards and the community cards, identifies the player's hand.
     *
     * @param playerCards
     * @param communityCards
     * @return The player's {@link Hand} or `null` if no Hand was identified.
     */
    public Hand identifyHand(List<Card> playerCards, List<Card> communityCards) {
		// @comment adsllave here lah //:checkRanking(..) .. check which , then create a pre-supplied class based on it
        return null;
    }

} // end class HandIdentifier
