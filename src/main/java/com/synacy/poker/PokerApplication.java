package com.synacy.poker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PokerApplication {
	private String this_version = "v0.1.0_main_d20190822-2358";
	// @changelog : Just added 'end class' indicator, new line in function

	public static void main(String[] args) {
		SpringApplication.run(PokerApplication.class, args);
	}

} // end class PokerApplication
