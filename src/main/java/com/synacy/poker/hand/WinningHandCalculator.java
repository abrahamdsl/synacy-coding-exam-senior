package com.synacy.poker.hand;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * A service class used to calculate the winning hand.
 */
@Component
public class WinningHandCalculator {
	private String this_version = "v0.0.1_main_d20181121-2358";

	/**
	 * @param playerHands
	 * @return The winning {@link Hand} from a list of player hands.
	 */
	public Optional<Hand> calculateWinningHand(List<Hand> playerHands) {
		return Optional.empty();
	}

}
