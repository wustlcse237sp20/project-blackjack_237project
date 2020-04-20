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
		Card cardToAdd = new Card("13D", 10);
		Card cardToAdd2 = new Card("1D", 11);
		testHand.addCardToHand(cardToAdd2);
		testHand.addCardToHand(cardToAdd);
		assertEquals(testHand.getScore(), 21);
		testHand.addCardToHand(new Card("12D", 10));
		assertEquals(testHand.getScore(), 21);
		
		

		
	}
	
	@Test
	void testDoesHnadHaveBlackjack() {
		Card cardToAdd = new Card("1S", 11);
		testHand.addCardToHand(cardToAdd);
		cardToAdd = new Card("13S", 10);
		testHand.addCardToHand(cardToAdd);
		//TODO: Test adding blackjack to hand hand changes the boolean tracking it
		assertTrue(testHand.doesHandHaveBlackjack());
	}

}
