package com.synacy.poker.hand;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * A service class used to calculate the winning hand.
 */
@Component
public class WinningHandCalculator {
	private String this_version = "v0.1.0_main_d20190815-2300";

	/**
	 * @param playerHands
	 * @return The winning {@link Hand} from a list of player hands.
	 */
	public Optional<Hand> calculateWinningHand(List<Hand> playerHands) {
		// @comment adsllave here lah 
		return Optional.empty();
	}

}
