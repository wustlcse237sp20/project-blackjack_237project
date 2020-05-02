package tests;
import blackjack.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTests {
	Player dealer;
	Deck testDeck;
	Player testPlayer;
	
	@BeforeEach
	void setUpInstance() {
		testPlayer = new Player();
		dealer = new Player();
		testDeck = new Deck(8);
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
<<<<<<< HEAD
		testPlayer.getHands().get(0).addCardToHand(new Card("9S", 9));
		testPlayer.getHands().get(0).addCardToHand(new Card("5S",5));
		dealer.getHands().get(0).addCardToHand(new Card("9S", 9));
		dealer.getHands().get(0).addCardToHand(new Card("5S",5));
		testPlayer.hitBasedOnRules(dealer, testDeck,1);   //
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 3);
		testPlayer.emptyHand();
=======
		testPlayer.getSingleHand(0).addCardToHand(new Card("9S", 9));
		testPlayer.getSingleHand(0).addCardToHand(new Card("5S", 5));
		dealer.getSingleHand(0).addCardToHand(new Card("9S", 9));
		dealer.getSingleHand(0).addCardToHand(new Card("5S",5));
>>>>>>> branch 'Soft_17' of https://github.com/wustlcse237sp20/project-blackjack_237project.git
		
<<<<<<< HEAD
		testPlayer.getHands().get(0).addCardToHand(new Card("9S", 9));
		testPlayer.getHands().get(0).addCardToHand(new Card("6S",6));
		testPlayer.hitBasedOnRules(dealer, testDeck,1); //
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		
		
	}
	
	@Test
	void testRuleHit2() {
		testPlayer.getHands().get(0).addCardToHand(new Card("9S", 9));
		testPlayer.getHands().get(0).addCardToHand(new Card("7S",7));
		dealer.getHands().get(0).addCardToHand(new Card("1S", 11));
		dealer.getHands().get(0).addCardToHand(new Card("5S",5));
		testPlayer.hitBasedOnRules(dealer, testDeck,2);
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 3);
		testPlayer.emptyHand();
		dealer.emptyHand();
	
		testPlayer.getHands().get(0).addCardToHand(new Card("9S", 9));
		testPlayer.getHands().get(0).addCardToHand(new Card("8S", 8));
		assertEquals(testPlayer.getHands().get(0).getScore(), 17);
		dealer.getHands().get(0).addCardToHand(new Card("1S", 11));
		dealer.getHands().get(0).addCardToHand(new Card("5S",5));
		testPlayer.hitBasedOnRules(dealer, testDeck,2);
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		testPlayer.emptyHand();
		dealer.emptyHand();
		
		testPlayer.getHands().get(0).addCardToHand(new Card("9S", 9));
		testPlayer.getHands().get(0).addCardToHand(new Card("7S",7));
		dealer.getHands().get(0).addCardToHand(new Card("5S", 5));
		dealer.getHands().get(0).addCardToHand(new Card("1S",11));
		testPlayer.hitBasedOnRules(dealer, testDeck,2);
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		testPlayer.emptyHand();
		dealer.emptyHand();
		
		testPlayer.getHands().get(0).addCardToHand(new Card("2S", 2));
		testPlayer.getHands().get(0).addCardToHand(new Card("9S",9));
		dealer.getHands().get(0).addCardToHand(new Card("5S", 5));
		dealer.getHands().get(0).addCardToHand(new Card("2S",2));
		testPlayer.hitBasedOnRules(dealer, testDeck,2);
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 3);
		testPlayer.emptyHand();
		dealer.emptyHand();
		
		
		
		
	}
	
	@Test
	void testRuleHit3() {
		testPlayer.getHands().get(0).addCardToHand(new Card("2S", 2));
		testPlayer.getHands().get(0).addCardToHand(new Card("5S",5));

		
		testPlayer.hitBasedOnRules(dealer, testDeck,3);
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 3);
=======
		//player has 14, should hit once
		testPlayer.hitBasedOnRules(dealer, testDeck,1);
		assertEquals(3, testPlayer.getCardsInSingleHand(0).size());
		
>>>>>>> branch 'Soft_17' of https://github.com/wustlcse237sp20/project-blackjack_237project.git
		testPlayer.emptyHand();
<<<<<<< HEAD
		testPlayer.getHands().get(0).addCardToHand(new Card("9S", 9));
		testPlayer.getHands().get(0).addCardToHand(new Card("6S",6));
		testPlayer.hitBasedOnRules(dealer, testDeck,3);
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		}

	
	
	@Test
	void testRuleHit4() {
		testPlayer.getHands().get(0).addCardToHand(new Card("1S", 11));
		testPlayer.getHands().get(0).addCardToHand(new Card("4S",4));
		dealer.getHands().get(0).addCardToHand(new Card("1S", 11));
		dealer.getHands().get(0).addCardToHand(new Card("5S",5));
		testPlayer.hitBasedOnRules(dealer, testDeck,4);
		assertNotEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		testPlayer.emptyHand();
		dealer.emptyHand();
	
		testPlayer.getHands().get(0).addCardToHand(new Card("4S", 4));
		testPlayer.getHands().get(0).addCardToHand(new Card("1S", 11));
		dealer.getHands().get(0).addCardToHand(new Card("1S", 11));
		dealer.getHands().get(0).addCardToHand(new Card("5S",5));
		testPlayer.hitBasedOnRules(dealer, testDeck,4);
		assertNotEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		testPlayer.emptyHand();
		dealer.emptyHand();
		
		testPlayer.getHands().get(0).addCardToHand(new Card("2S", 2));
		testPlayer.getHands().get(0).addCardToHand(new Card("7S",7));
		dealer.getHands().get(0).addCardToHand(new Card("5S", 5));
		dealer.getHands().get(0).addCardToHand(new Card("1S",11));
		testPlayer.hitBasedOnRules(dealer, testDeck,4);
		assertNotEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		testPlayer.emptyHand();
		dealer.emptyHand();
		
		testPlayer.getHands().get(0).addCardToHand(new Card("1S", 11));
		testPlayer.getHands().get(0).addCardToHand(new Card("9S",9));
		dealer.getHands().get(0).addCardToHand(new Card("5S", 5));
		dealer.getHands().get(0).addCardToHand(new Card("2S",2));
		testPlayer.hitBasedOnRules(dealer, testDeck,4);
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		testPlayer.emptyHand();
		dealer.emptyHand();
		
		
=======
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
>>>>>>> branch 'Soft_17' of https://github.com/wustlcse237sp20/project-blackjack_237project.git
		
	}
<<<<<<< HEAD
	
	@Test
	void testRuleHit5() {
		testPlayer.getHands().get(0).addCardToHand(new Card("9S", 9));
		testPlayer.getHands().get(0).addCardToHand(new Card("7S",7));
		testPlayer.hitBasedOnRules(dealer, testDeck,5);   //
		assertNotEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		testPlayer.emptyHand();
		
		testPlayer.getHands().get(0).addCardToHand(new Card("9S", 9));
		testPlayer.getHands().get(0).addCardToHand(new Card("8S",8));
		testPlayer.hitBasedOnRules(dealer, testDeck,1); //
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
	}
	
=======
>>>>>>> branch 'Soft_17' of https://github.com/wustlcse237sp20/project-blackjack_237project.git

}
