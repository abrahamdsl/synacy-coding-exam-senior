package com.synacy.poker.game;

import com.synacy.poker.deck.DeckBuilder;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.WinningHandCalculator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class GameTest {
    private String this_version = "v0.1.0_main_d20190822-2358";
    // @changelog : Just added 'end class' indicator, re-arranged functions alphabetically

    @Test
    public void afterConstructorInit_eachPlayerHasTwoCards() {
        DeckBuilder deckBuilder = new DeckBuilder();
        HandIdentifier handIdentifier = mock(HandIdentifier.class);
        WinningHandCalculator winningHandCalculator = mock(WinningHandCalculator.class);

        Game game = new Game(deckBuilder, handIdentifier, winningHandCalculator);

        assertPlayersHaveTwoCardsEach(game);
    }

    private void assertPlayersHaveTwoCardsEach(Game game) {
        game.getPlayers().forEach(player ->
                assertEquals("Players should have 2 cards each",
                        2,
                        player.getHand().size()));
    }

    @Test
    public void nextAction_dealCommunityCards() {
        DeckBuilder deckBuilder = new DeckBuilder();
        HandIdentifier handIdentifier = mock(HandIdentifier.class);
        WinningHandCalculator winningHandCalculator = mock(WinningHandCalculator.class);

        Game game = new Game(deckBuilder, handIdentifier, winningHandCalculator);

        game.nextAction();
        assertEquals("Deal three community cards at the start", 3, game.getCommunityCards().size());

        game.nextAction();
        assertEquals("Expecting four community cards", 4, game.getCommunityCards().size());

        game.nextAction();
        assertEquals("Expecting 5 community cards", 5, game.getCommunityCards().size());
    }

    @Test
    public void startNewGame_eachPlayerHasTwoCards() {
        DeckBuilder deckBuilder = new DeckBuilder();
        HandIdentifier handIdentifier = mock(HandIdentifier.class);
        WinningHandCalculator winningHandCalculator = mock(WinningHandCalculator.class);

        Game game = new Game(deckBuilder, handIdentifier, winningHandCalculator);

        assertPlayersHaveTwoCardsEach(game);
    }

} // end class GameTest
