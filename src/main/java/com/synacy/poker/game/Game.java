package com.synacy.poker.game;

import com.synacy.poker.card.Card;
import com.synacy.poker.deck.Deck;
import com.synacy.poker.deck.DeckBuilder;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.WinningHandCalculator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The game engine.
 */
@Component
public class Game {
    private String this_version = "v0.6.0_main_d20190906-2200";
    // @changelog : No significant change except debugging display
    //   "The winning hand is <hand>" in identifyWinningHand()

    private static final int MAX_PLAYER_CARDS = 2;
    private static final int MAX_COMMUNITY_CARDS = 5;

    private List<Card> communityCards = new ArrayList<>();

    private Deck deck;
    private DeckBuilder deckBuilder;

    private HandIdentifier handIdentifier;

    private List<Player> players = new ArrayList<>();

    private Hand winningHand = null;
    private WinningHandCalculator winningHandCalculator;

    public Game(DeckBuilder deckBuilder,
                HandIdentifier handIdentifier,
                WinningHandCalculator winningHandCalculator) {
        players.add(new Player("Alex"));
        players.add(new Player("Bob"));
        players.add(new Player("Jane"));

        this.deckBuilder = deckBuilder;
        this.handIdentifier = handIdentifier;
        this.winningHandCalculator = winningHandCalculator;

        startNewGame();
    }

    private void burnCard() {
        deck.removeFromTop();
    }

    /**
     * Checks if the player won
     *
     * @param player
     * @return true if the player's hand is equal to the winning hand.
     */
    public boolean checkIfPlayerWon(Player player) {
        Hand playerHand = identifyPlayerHand(player);
        return winningHand != null && winningHand.equals(playerHand);
    }

    private void dealHands() {
        for (int i = 0; i < MAX_PLAYER_CARDS; i++) {
            dealOneCardToEachPlayer();
        }
    }

    private void dealOneCardToEachPlayer() {
        players.forEach(player -> player.addToHand(deck.removeFromTop()));
    }

    private void dealOneCommunityCard() {
        communityCards.add(deck.removeFromTop());
    }

    private void dealThreeCommunityCards() {
        communityCards.add(deck.removeFromTop());
        communityCards.add(deck.removeFromTop());
        communityCards.add(deck.removeFromTop());
    }

    /**
     * @return The list of community cards {@link Card}
     */
    public List<Card> getCommunityCards() {
        return communityCards;
    }

    /**
     * @return The list of {@link Player}s
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return true if the number of community cards is equal to the maximum community cards allowed.
     */
    public boolean hasEnded() {
        return communityCards.size() >= MAX_COMMUNITY_CARDS;
    }

    /**
     * Identifies the player's hand. A hand is combination of the two cards in the player's
     * possession and the community cards on the table.
     *
     * @param player
     * @return The {@link} of a player, e.g. High Card, One Pair, Straight, etc.
     * @see <a href="https://www.youtube.com/watch?v=GAoR9ji8D6A">Poker rules</a>
     */
    // @comment adsllave might be refactored
    public Hand identifyPlayerHand(Player player) {
        List<Card> playerCards = player.getHand();
        return handIdentifier.identifyHand(playerCards, communityCards);
    }

    /**
     * Checks the combination of the players and community cards to identify the winning hand.
     *
     * @see <a href="https://www.youtube.com/watch?v=GAoR9ji8D6A">Poker rules</a>
     */
    public void identifyWinningHand() {
        List<Hand> playerHands = players.stream()
                .map(this::identifyPlayerHand)
                .collect(Collectors.toList());
        Optional<Hand> optionalHand = winningHandCalculator.calculateWinningHand(playerHands);
        winningHand = optionalHand.orElse(null);

        // @todo: debugging code, remove later
        if( winningHand != null ){
          System.out.printf("The winning hand is [%s]\n", winningHand);
        }
    }

    /**
     * The action to take after a new game has been started.
     *
     * <ol>
     * <li>Deal three community cards</li>
     * <li>Deal one community card</li>
     * <li>Deal another community card</li>
     * <li>Determine the winner/s</li>
     * </ol>
     * <p>
     * Dealt community are of course removed from the deck at the time their placed on the table.
     */
    public void nextAction() {
        if (communityCards.isEmpty()) {
            burnCard();
            dealThreeCommunityCards();
        } else if (communityCards.size() < MAX_COMMUNITY_CARDS) {
            burnCard();
            dealOneCommunityCard();
        }

        if (hasEnded()) {
            identifyWinningHand();
        }
    }

    /**
     * Starts a new game.
     *
     * <h3>The following describes a new game.</h3>
     * <ul>
     * <li>Players' previous hands are cleared</li>
     * <li>Community cards are cleared</li>
     * <li>A new deck is used</li>
     * <li>The deck is shuffled</li>
     * <li>Players' are dealt with new cards.</li>
     * </ul>
     */
    public void startNewGame() {
        players.forEach(Player::clearHand);
        communityCards.clear();

        deck = deckBuilder.buildDeck();
        deck.shuffle();

        dealHands();
    }
} // end class Game
