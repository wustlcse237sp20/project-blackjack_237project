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
	void testAddingAnAceToAHand() {
		Card cardToAdd = new Card("1S", 11);
		testHand.addCardToHand(cardToAdd);
		assertEquals(testHand.containsAce(), true);
	}

}
