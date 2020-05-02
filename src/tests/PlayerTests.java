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
		testPlayer.getHands().get(0).addCardToHand(new Card("9S", 9));
		testPlayer.getHands().get(0).addCardToHand(new Card("5S",5));
		dealer.getHands().get(0).addCardToHand(new Card("9S", 9));
		dealer.getHands().get(0).addCardToHand(new Card("5S",5));
		assertEquals(testPlayer.getHands().get(0).getScore(), 14);
		testPlayer.hitBasedOnRules(dealer, testDeck,1);   //
	}
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 3);
		testPlayer.emptyHand();
		
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 0);
		testPlayer.getHands().get(0).addCardToHand(new Card("9S", 9));
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 1);
		
		testPlayer.getHands().get(0).addCardToHand(new Card("6S",6));
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		assertEquals(testPlayer.getSingleHand(0).getScore(), 15);
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
		testPlayer.getHands().get(0).addCardToHand(new Card("7S",7));
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

		
		testPlayer.hitBasedOnRules(dealer, testDeck,1);
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 3);
		testPlayer.emptyHand();
		testPlayer.getHands().get(0).addCardToHand(new Card("9S", 9));
		testPlayer.getHands().get(0).addCardToHand(new Card("6S",6));
		testPlayer.hitBasedOnRules(dealer, testDeck,1);
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		}

	
	
	@Test
	void testRuleHit4() {
		ArrayList<Player> testPlayers = new ArrayList<Player>();
		testPlayers.add(dealer);
		testPlayers.add(testPlayer);
		Deck testDeck = new Deck(8);
		testDeck.dealOutHands(testPlayers);
		testPlayer.hitBasedOnRules(dealer,testDeck, 4);
		if((testPlayer.getSingleHand(0).getScore() > 6&&testPlayer.getSingleHand(0).getScore()<16)&&(testPlayer.getHands().get(0).get(0).getValue()==11||testPlayer.getHands().get(0).get(1).getValue()==11)) {
		assertNotEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		}
		else {
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		}
	}
	
	@Test
	void testRuleHit5() {
		ArrayList<Player> testPlayers = new ArrayList<Player>();
		testPlayers.add(dealer);
		testPlayers.add(testPlayer);
		Deck testDeck = new Deck(8);
		testDeck.dealOutHands(testPlayers);
		testPlayer.hitBasedOnRules(dealer, testDeck,5);
		if(testPlayer.getSingleHand(0).getScore()<=16) {
		assertNotEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		}
		else {
		assertEquals(testPlayer.getHands().get(0).getCardsInHand().size(), 2);
		}
	}
	

}
