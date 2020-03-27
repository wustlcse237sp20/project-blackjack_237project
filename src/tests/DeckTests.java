package tests;
import blackjack.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DeckTests {
	Deck deck = new Deck();
	
	@Test
	void testSettingNumberOfDecks() {
		for(int i = 0; i < 11; i++) {
			deck.setNumberOfDecks(i);
			if(i < 1 || i > 8) {
				assertEquals(1, deck.getNumberOfDecks());
			} else {
				assertEquals(i, deck.getNumberOfDecks());
			}
		}
	}

}
