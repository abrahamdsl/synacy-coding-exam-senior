package com.synacy.poker.game;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.card.CardSuit;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.types.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HandIdentifierTest {
	private String this_version = "v0.4.0_main_d20190825-2358";
	// @changelog : Corrected wrong left operand in a RoyalFlush test

	private HandIdentifier handIdentifier = new HandIdentifier();

	@Test
	public void identifyHand_flush() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.SEVEN, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.SPADES)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.SPADES),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.ACE, CardSuit.CLUBS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof Flush);
		assertEquals("Flush (Q High)", identifiedHand.toString());
	}

	@Test
	public void identifyHand_fourOfAKind() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.EIGHT, CardSuit.SPADES),
				new Card(CardRank.EIGHT, CardSuit.CLUBS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.EIGHT, CardSuit.DIAMONDS),
				new Card(CardRank.EIGHT, CardSuit.HEARTS),
				new Card(CardRank.SEVEN, CardSuit.SPADES),
				new Card(CardRank.SIX, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.CLUBS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof FourOfAKind);
		assertEquals("Quads (8) - 7 High", identifiedHand.toString());
	}

	@Test
	public void identifyHand_fullHouse() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.EIGHT, CardSuit.SPADES),
				new Card(CardRank.EIGHT, CardSuit.DIAMONDS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.SEVEN, CardSuit.HEARTS),
				new Card(CardRank.SEVEN, CardSuit.SPADES),
				new Card(CardRank.EIGHT, CardSuit.CLUBS),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.ACE, CardSuit.CLUBS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof FullHouse);
		assertEquals("Full House (8,7)", identifiedHand.toString());
	}

	@Test
	public void identifyHand_highCard() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.ACE, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.CLUBS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.DIAMONDS),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.THREE, CardSuit.SPADES),
				new Card(CardRank.SIX, CardSuit.DIAMONDS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof HighCard);
		assertEquals("A,Q,J,10,6", identifiedHand.toString());
	}

	@Test
	public void identifyHand_onePair() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.TWO, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.CLUBS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.DIAMONDS),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.THREE, CardSuit.SPADES),
				new Card(CardRank.SIX, CardSuit.DIAMONDS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof OnePair);
		assertEquals("One Pair (2) - Q,J,10 High", identifiedHand.toString());
	}

	@Test
	public void identifyHand_royalFlush() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.ACE, CardSuit.SPADES),
				new Card(CardRank.KING, CardSuit.SPADES)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.SPADES),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.ACE, CardSuit.CLUBS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		// ha, oopsie, this should have been RoyalFlush - that's why I'm in circles!
		assertTrue(identifiedHand instanceof RoyalFlush);
		assertEquals("Royal Flush", identifiedHand.toString());
	}

	@Test
	public void identifyHand_straight() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.EIGHT, CardSuit.SPADES),
				new Card(CardRank.KING, CardSuit.CLUBS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.DIAMONDS),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.CLUBS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof Straight);
		assertEquals("Straight (K High)", identifiedHand.toString());
	}

	@Test
	public void identifyHand_straightFlush() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.SPADES),
				new Card(CardRank.JACK, CardSuit.SPADES)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.EIGHT, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.SPADES),
				new Card(CardRank.ACE, CardSuit.CLUBS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof StraightFlush);
		assertEquals("Straight Flush (Q High)", identifiedHand.toString());
	}

	@Test
	public void identifyHand_threeOfAKind() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.TWO, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.CLUBS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.DIAMONDS),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.DIAMONDS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof ThreeOfAKind);
		assertEquals("Trips (2) - Q,J High", identifiedHand.toString());
	}

	@Test
	public void identifyHand_twoPair() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.TWO, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.CLUBS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.DIAMONDS),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.DIAMONDS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof TwoPair);
		assertEquals("Two Pair (9,2) - Q High", identifiedHand.toString());
	}

} // end class HandIdentifierTest
