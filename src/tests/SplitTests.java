package tests;
import blackjack.*;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SplitTests {
	private class MockedBlackjack extends Blackjack {
		@Override
		protected void setupGameParameters() {
			setNumberOfComputerPlayers(0);
			setDeck(1);
			setStartingChipNumber(100);
			setDisplayGUI(false);
			playAHand();
		}
		@Override
        protected void setBetAmount() {
			getUser().setBet(50);
			getUser().subtractChips(50);
        }
		@Override
        protected boolean startNewHand() {
        	return false;
        }
    }
	private MockedBlackjack testGame;
	
	@BeforeEach
	void setupGameInstance() {
		testGame = new MockedBlackjack();
	}
	
	@Test
	void testSplittingHand() {
		testGame.getUser().emptyHand();
		Card testCard = new Card("10S", 10);
		for(int i = 0; i < 2; i++) {
			testGame.getSingleUserHand(0).addCardToHand(testCard);
		}
		testGame.handleSplitPress();
		testGame.getUserInterface().incrementControllingHandNumber();
		assertEquals(2, testGame.getUserHands().size()); //check there are now two hands
		for(int i = 0; i < 2; i++) {
			assertEquals(2, testGame.getUser().getCardsInSingleHand(i).size()); //check both hands have two cards
			assertEquals(testCard, testGame.getUser().getCardsInSingleHand(i).get(0)); //check first card came from the original hand
			assertNotEquals(testCard, testGame.getUser().getCardsInSingleHand(i).get(1)); //check second card is not from the original hand
		}
	}
	
	@Test
	void testWinningFirstHand() {
		testGame.getUser().emptyHand();
		Card tenOfSpades = new Card("10S", 10);
		Card nineOfSpades = new Card("9S", 9);
		
		testGame.getDealer().emptyHand();
		testGame.getDealerHands().get(0).addCardToHand(tenOfSpades);
		testGame.getDealerHands().get(0).addCardToHand(nineOfSpades); //score of dealer's hand is 19
		
		for(int i = 0; i < 2; i++) {
			testGame.getSingleUserHand(0).addCardToHand(tenOfSpades);
		}
		testGame.handleSplitPress();
		testGame.getUserInterface().incrementControllingHandNumber();
		
		testGame.getSingleUserHand(0).removeCardFromHand(1);
		testGame.getSingleUserHand(0).addCardToHand(tenOfSpades); //score of first hand is 20
		testGame.handleStandPress();
		
		while(testGame.getUserHandScore(testGame.getSingleUserHand(1)) < 22) { //force bust on second hand
			testGame.handleHitPress();
		}		
		
		assertEquals(false, testGame.isHandWon(1));
		assertEquals(true, testGame.isHandWon(0));
		assertEquals(false, testGame.isHandPushed(0));
		assertEquals(false, testGame.isHandPushed(1));
		assertEquals(100, testGame.getUserNumberOfChips());
	}
	
	@Test
	void testWinningSecondHand() {
		testGame.getUser().emptyHand();
		Card tenOfSpades = new Card("10S", 10);
		Card nineOfSpades = new Card("9S", 9);
		
		testGame.getDealer().emptyHand();
		testGame.getDealerHands().get(0).addCardToHand(tenOfSpades);
		testGame.getDealerHands().get(0).addCardToHand(nineOfSpades); //score of dealer's hand is 19
		
		for(int i = 0; i < 2; i++) {
			testGame.getSingleUserHand(0).addCardToHand(tenOfSpades);
		}
		testGame.handleSplitPress();
		testGame.getUserInterface().incrementControllingHandNumber();
		while(testGame.getUserHandScore(testGame.getSingleUserHand(0)) < 22) { //force bust on first hand
			testGame.handleHitPress();
		}
		
		testGame.getSingleUserHand(1).removeCardFromHand(1);
		testGame.getSingleUserHand(1).addCardToHand(tenOfSpades); //score of second hand is 20
		testGame.handleStandPress();
		
		assertEquals(false, testGame.isHandWon(0));
		assertEquals(true, testGame.isHandWon(1));
		assertEquals(false, testGame.isHandPushed(0));
		assertEquals(false, testGame.isHandPushed(1));
		assertEquals(100, testGame.getUserNumberOfChips());
	}
	
	@Test
	void testWinningBothHands() {
		testGame.getUser().emptyHand();
		Card tenOfSpades = new Card("10S", 10);
		Card nineOfSpades = new Card("9S", 9);
		
		testGame.getDealer().emptyHand();
		testGame.getDealerHands().get(0).addCardToHand(tenOfSpades);
		testGame.getDealerHands().get(0).addCardToHand(nineOfSpades); //score of dealer's hand is 19
		
		for(int i = 0; i < 2; i++) {
			testGame.getSingleUserHand(0).addCardToHand(tenOfSpades);
		}
		testGame.handleSplitPress();
		testGame.getUserInterface().incrementControllingHandNumber();
		
		testGame.getSingleUserHand(0).removeCardFromHand(1);
		testGame.getSingleUserHand(0).addCardToHand(tenOfSpades); //score of first hand is 20
		testGame.handleStandPress();
		
		testGame.getSingleUserHand(1).removeCardFromHand(1);
		testGame.getSingleUserHand(1).addCardToHand(tenOfSpades); //score of second hand is 20
		testGame.handleStandPress();
		
		assertEquals(true, testGame.isHandWon(0));
		assertEquals(true, testGame.isHandWon(1));
		assertEquals(false, testGame.isHandPushed(0));
		assertEquals(false, testGame.isHandPushed(1));
		assertEquals(200, testGame.getUserNumberOfChips());
	}
	
	@Test
	void testLosingBothHandsByBust() {
		testGame.handleSplitPress();
		testGame.getUserInterface().incrementControllingHandNumber();
		
		while(testGame.getUserHandScore(testGame.getSingleUserHand(0)) < 22) { //force bust on first hand
			testGame.handleHitPress();
		}
		while(testGame.getUserHandScore(testGame.getSingleUserHand(1)) < 22) { //force bust on second hand
			testGame.handleHitPress();
		}
		assertEquals(false, testGame.isHandWon(0));
		assertEquals(false, testGame.isHandWon(1));
		assertEquals(false, testGame.isHandPushed(0));
		assertEquals(false, testGame.isHandPushed(1));
		assertEquals(0, testGame.getUserNumberOfChips());
	}
	
	@Test
	void testLosingBothHandsByScore() {
		testGame.getUser().emptyHand();
		Card tenOfSpades = new Card("10S", 10);
		Card nineOfSpades = new Card("9S", 9);
		Card eightOfSpades = new Card("8S", 8);
		
		testGame.getDealer().emptyHand();
		testGame.getDealerHands().get(0).addCardToHand(tenOfSpades);
		testGame.getDealerHands().get(0).addCardToHand(nineOfSpades); //score of dealer's hand is 19
		
		for(int i = 0; i < 2; i++) {
			testGame.getSingleUserHand(0).addCardToHand(tenOfSpades);
		}
		testGame.handleSplitPress();
		testGame.getUserInterface().incrementControllingHandNumber();
		
		testGame.getSingleUserHand(0).removeCardFromHand(1);
		testGame.getSingleUserHand(0).addCardToHand(eightOfSpades); //score of first hand is 18
		testGame.handleStandPress();
		
		testGame.getSingleUserHand(1).removeCardFromHand(1);
		testGame.getSingleUserHand(1).addCardToHand(eightOfSpades); //score of second hand is 18
		testGame.handleStandPress();
		
		assertEquals(false, testGame.isHandWon(0));
		assertEquals(false, testGame.isHandWon(1));
		assertEquals(false, testGame.isHandPushed(0));
		assertEquals(false, testGame.isHandPushed(1));
		assertEquals(0, testGame.getUserNumberOfChips());
	}
	
	@Test
	void testPushingBothHands() {
		testGame.getUser().emptyHand();
		Card tenOfSpades = new Card("10S", 10);
		Card nineOfSpades = new Card("9S", 9);
		
		testGame.getDealer().emptyHand();
		testGame.getDealerHands().get(0).addCardToHand(tenOfSpades);
		testGame.getDealerHands().get(0).addCardToHand(nineOfSpades); //score of dealer's hand is 19
		
		for(int i = 0; i < 2; i++) {
			testGame.getSingleUserHand(0).addCardToHand(tenOfSpades);
		}
		testGame.handleSplitPress();
		testGame.getUserInterface().incrementControllingHandNumber();
		
		testGame.getSingleUserHand(0).removeCardFromHand(1);
		testGame.getSingleUserHand(0).addCardToHand(nineOfSpades); //score of first hand is 19
		testGame.handleStandPress();
		
		testGame.getSingleUserHand(1).removeCardFromHand(1);
		testGame.getSingleUserHand(1).addCardToHand(nineOfSpades); //score of second hand is 19
		testGame.handleStandPress();
		
		assertEquals(false, testGame.isHandWon(0));
		assertEquals(false, testGame.isHandWon(1));
		assertEquals(true, testGame.isHandPushed(0));
		assertEquals(true, testGame.isHandPushed(1));
		assertEquals(100, testGame.getUserNumberOfChips());
	}
	
	@Test
	void testWinningHandByBlackjack() {
		testGame.getUser().emptyHand();
		Card aceOfSpades = new Card("1S", 11);
		Card tenOfSpades = new Card("10S", 10);
		Card nineOfSpades = new Card("9S", 9);
		
		testGame.getDealer().emptyHand();
		testGame.getDealerHands().get(0).addCardToHand(tenOfSpades);
		testGame.getDealerHands().get(0).addCardToHand(nineOfSpades); //score of dealer's hand is 19
		
		for(int i = 0; i < 2; i++) {
			testGame.getSingleUserHand(0).addCardToHand(tenOfSpades);
		}
		testGame.handleSplitPress();
		testGame.getUserInterface().incrementControllingHandNumber();
		
		testGame.getSingleUserHand(0).removeCardFromHand(1);
		testGame.getSingleUserHand(0).addCardToHand(nineOfSpades); //score of first hand is 19 - push
		testGame.handleStandPress();
		
		testGame.getSingleUserHand(1).removeCardFromHand(1);
		testGame.getSingleUserHand(1).addCardToHand(aceOfSpades); //score of second hand is BJ
		testGame.handleStandPress();
		
		assertEquals(false, testGame.isHandWon(0));
		assertEquals(true, testGame.isHandWon(1));
		assertEquals(true, testGame.isHandPushed(0));
		assertEquals(false, testGame.isHandPushed(1));
		assertEquals(175, testGame.getUserNumberOfChips());
	}

}
