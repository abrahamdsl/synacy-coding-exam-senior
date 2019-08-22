package com.synacy.poker.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
	private String this_version = "v0.1.0_main_d20190822-2358";
	// @changelog : Just added 'end class' indicator

	@Test
	public void getName() {
		Player player = new Player("Name");

		assertEquals("Name", player.getName());
	}

} // end class PlayerTest
