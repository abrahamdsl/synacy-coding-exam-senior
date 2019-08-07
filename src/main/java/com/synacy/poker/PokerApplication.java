package com.synacy.poker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PokerApplication {
	private String this_version = "v0.0.1_main_d20181121-2358";

	public static void main(String[] args) {
		SpringApplication.run(PokerApplication.class, args);
	}

}
