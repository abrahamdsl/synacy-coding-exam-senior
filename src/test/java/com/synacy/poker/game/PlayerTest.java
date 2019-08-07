package com.synacy.poker.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
	private String this_version = "v0.0.1_main_d20181121-2358";

	@Test
	public void getName() {
		Player player = new Player("Name");

		assertEquals("Name", player.getName());
	}

}
