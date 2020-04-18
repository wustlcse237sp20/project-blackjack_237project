package tests;
import blackjack.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HandTests {
	
	Hand testHand;
	
	@BeforeEach
	void setUpInstance() {
		testHand = new Hand();
	}
	
	@Test
	void testAddingACardToAHand() {
		Card cardToAdd = new Card("13D", 10);
		testHand.addCardToHand(cardToAdd);
		assertEquals(testHand.getCardsInHand().contains(cardToAdd), true);
	}
	
	@Test
	void testChangingScoreOfAces() {
		fail("Not yet implemented");
	}
	
	@Test
	void testDoesHnadHaveBlackjack() {
		Card cardToAdd = new Card("1S", 11);
		testHand.addCardToHand(cardToAdd);
		cardToAdd = new Card("KS", 10);
		testHand.addCardToHand(cardToAdd);
		//TODO: Test adding blackjack to hand hand changes the boolean tracking it
		fail("Not yet implemented");
	}

}
