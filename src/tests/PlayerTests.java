package tests;
import blackjack.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTests {
	Player dealer;
	Deck testDeck = new Deck(8);
	Player testPlayer;
	
	@BeforeEach
	void setUpInstance() {
		testPlayer = new Player();
		dealer = new Player();
	}
	
	@Test
	void testHit() {
		ArrayList<Player> testPlayers = new ArrayList<Player>();
		testPlayers.add(testPlayer);
		Deck testDeck = new Deck(8);
		testDeck.dealOutHands(testPlayers);
		int scoreBeforeHit = testPlayer.getSingleHand(0).getScore();
		testPlayer.hit(testDeck, 0);
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 3);
		assertNotEquals(testPlayer.getSingleHand(0).getScore(), scoreBeforeHit);
	}

	@Test
	void testRuleHit1() {
		testPlayer.getSingleHand(0).addCardToHand(new Card("9S", 9));
		testPlayer.getSingleHand(0).addCardToHand(new Card("5S", 5));
		dealer.getSingleHand(0).addCardToHand(new Card("9S", 9));
		dealer.getSingleHand(0).addCardToHand(new Card("5S",5));
		
		//player has 14, should hit once
		testPlayer.hitBasedOnRules(dealer, testDeck,1);
		assertEquals(3, testPlayer.getCardsInSingleHand(0).size());
		
		testPlayer.emptyHand();
		testPlayer.getSingleHand(0).addCardToHand(new Card("9S", 9));
		testPlayer.getSingleHand(0).addCardToHand(new Card("6S", 6));
		
		//player has 15, shouldnt hit
		testPlayer.hitBasedOnRules(dealer, testDeck,1);
		assertEquals(2, testPlayer.getCardsInSingleHand(0).size());
		
		testPlayer.emptyHand();
		testPlayer.getSingleHand(0).addCardToHand(new Card("9S", 9));
		testPlayer.getSingleHand(0).addCardToHand(new Card("6S", 7));
		
		//player has 16, shouldnt hit
		testPlayer.hitBasedOnRules(dealer, testDeck,1);
		assertEquals(2, testPlayer.getCardsInSingleHand(0).size());
		
	}

}
