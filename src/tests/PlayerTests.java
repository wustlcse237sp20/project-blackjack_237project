package tests;
import blackjack.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTests {
	
	Player testPlayer;
	
	@BeforeEach
	void setUpInstance() {
		testPlayer = new Player();
	}
	
	@Test
	void testAddingACardToAHand() {
		for(int i = 1; i < 14; i++) {
			int value;
			if(i == 1) {
				value = 11;
			} else if(i < 11) {
				value = i;
			} else {
				value = 10;
			}
			Card cardToAdd = new Card(i + "S", value);
			testPlayer.addCardToHand(cardToAdd);
			assertEquals(testPlayer.getHand().contains(cardToAdd), true);
			assertEquals(testPlayer.getPlayerScore(), value);
			testPlayer.emptyHand();
		}
	}
	
	@Test
	void testHit() {
		ArrayList<Player> testPlayers = new ArrayList<Player>();
		testPlayers.add(testPlayer);
		Deck testDeck = new Deck(8);
		testDeck.dealOutHands(testPlayers);
		int scoreBeforeHit = testPlayer.getPlayerScore();
		testPlayer.hit(testDeck);
		assertEquals(testPlayer.getHand().size(), 3);
		assertNotEquals(testPlayer.getPlayerScore(), scoreBeforeHit);
	}
	
	@Test
	void testChangingScoreOfAces() {
		fail("Not yet implemented");
	}
	
	@Test
	void testPlayerGetsBlackjack() {
		fail("Not yet implemented");
	}
	
	@Test
	void testDealerPlaysOutHand() {
		fail("Not yet implemented");
	}

}
