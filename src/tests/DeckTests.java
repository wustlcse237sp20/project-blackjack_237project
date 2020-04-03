package tests;
import blackjack.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class DeckTests {
	
	@Test
	void testSettingNumberOfDecks() {
		
		for(int i = 0; i < 11; i++) {
			Deck deck = new Deck();
			deck.setNumberOfDecks(i);
			if(i < 1 || i > 8) {
				assertEquals(1, deck.getNumberOfDecks());
			} else {
				assertEquals(i, deck.getNumberOfDecks());
			}
		}
	}
	
	
	@Test
	void testNewDeck() {
		for(int i = 0; i < 11; i++) {
			Deck deck = new Deck();
			deck.setNumberOfDecks(i);
			if(i>0&&i<9)
				assertEquals(52*i, deck.allCardsInGame.size());
			else
				assertEquals(52, deck.allCardsInGame.size());
		}
	}
	
	
	@Test
	void testShuffle(){
		for(int i = 0; i < 11; i++) {
			Deck deck = new Deck();
			deck.setNumberOfDecks(i);
			ArrayList<Card> copy = (ArrayList<Card>) deck.allCardsInGame.clone();
			deck.shuffle();
			assertNotEquals(copy, deck.allCardsInGame);
			
		}
	}
}
