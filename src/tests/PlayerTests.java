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
	void testHit() {
		ArrayList<Player> testPlayers = new ArrayList<Player>();
		testPlayers.add(testPlayer);
		Deck testDeck = new Deck(8);
		testDeck.dealOutHands(testPlayers);
		int scoreBeforeHit = testPlayer.getSingleHand(0).getScore();
		testPlayer.hit(testDeck, 0);
		assertEquals(testPlayer.getCardsInSingleHand(0).size(), 3);
		assertNotEquals(testPlayer.getSingleHand(0).getScore(), scoreBeforeHit);
	}

	@Test
	void testDealerPlaysOutHand() {
		fail("Not yet implemented");
	}

}
