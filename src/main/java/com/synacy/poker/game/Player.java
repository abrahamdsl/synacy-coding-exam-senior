package com.synacy.poker.game;

import com.synacy.poker.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * A player in the game.
 */
public class Player {
	private String this_version = "v0.1.0_main_d20190822-2358";
	// @changelog : Just re-arranged variables, constructors, methods according to alphabetical order and well-known
	//   standards.

	private List<Card> hand = new ArrayList<>();
	private String name;

	public Player(String name) {
		this.name = name;
	}

	void addToHand(Card card) {
		hand.add(card);
	}

	void clearHand() {
		hand.clear();
	}

	public List<Card> getHand() {
		return hand;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}
} //end class Player
