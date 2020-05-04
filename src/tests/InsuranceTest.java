package tests;
import blackjack.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InsuranceTest {
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
			getUser().setBet(10);
			getUser().subtractChips(10);
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
	void testInsuranceOnDealerBlackjack() {
		Card ace = new Card("1S", 11);
		Card ten = new Card("10S", 10);
		
		testGame.getUser().emptyHand();
		testGame.getDealer().emptyHand();
		//user has pair of tens
		testGame.getSingleUserHand(0).addCardToHand(ten);
		testGame.getSingleUserHand(0).addCardToHand(ten);
		//delaer has BJ
		testGame.getDealerHands().get(0).addCardToHand(ace);
		testGame.getDealerHands().get(0).addCardToHand(ten);
		
		testGame.handleInsurancePress();
		testGame.handleStandPress();
		
		assertEquals(95, testGame.getUserNumberOfChips());
		assertEquals(false, testGame.isHandWon(0));
	}
	@Test
	void testInsuranceOnDealerNotBlackjackUserLoss() {
		Card ace = new Card("1S", 11);
		Card ten = new Card("10S", 10);
		Card seven = new Card("7S", 7);
		
		testGame.getUser().emptyHand();
		testGame.getDealer().emptyHand();
		//user has 17
		testGame.getSingleUserHand(0).addCardToHand(ten);
		testGame.getSingleUserHand(0).addCardToHand(seven);
		//delaer has pair A7 (18)
		testGame.getDealerHands().get(0).addCardToHand(ace);
		testGame.getDealerHands().get(0).addCardToHand(seven);
		
		testGame.handleInsurancePress();
		testGame.handleStandPress();
		
		assertEquals(85, testGame.getUserNumberOfChips());
		assertEquals(false, testGame.isHandWon(0));
	}
	@Test
	void testInsuranceOnBlackjackPush() {
		Card ace = new Card("1S", 11);
		Card ten = new Card("10S", 10);
		
		testGame.getUser().emptyHand();
		testGame.getDealer().emptyHand();
		//user has pair of tens
		testGame.getSingleUserHand(0).addCardToHand(ace);
		testGame.getSingleUserHand(0).addCardToHand(ten);
		//delaer has BJ
		testGame.getDealerHands().get(0).addCardToHand(ace);
		testGame.getDealerHands().get(0).addCardToHand(ten);
		
		testGame.handleInsurancePress();
		testGame.handleStandPress();
		
		assertEquals(105, testGame.getUserNumberOfChips());
		assertEquals(false, testGame.isHandWon(0));
		assertEquals(true, testGame.isHandPushed(0));
	}
	@Test
	void testInsuranceOnDealerNotBlackjackUserWin() {
		Card ace = new Card("1S", 11);
		Card eight = new Card("8S", 8);
		Card seven = new Card("7S", 7);
				
		testGame.getUser().emptyHand();
		testGame.getDealer().emptyHand();
		//user has 19
		testGame.getSingleUserHand(0).addCardToHand(ace);
		testGame.getSingleUserHand(0).addCardToHand(eight);
		//delaer has 18
		testGame.getDealerHands().get(0).addCardToHand(ace);
		testGame.getDealerHands().get(0).addCardToHand(seven);
				
		testGame.handleInsurancePress();
		testGame.handleStandPress();
				
		assertEquals(105, testGame.getUserNumberOfChips());
		assertEquals(true, testGame.isHandWon(0));
	}
	@Test
	void testInsuranceOnDealerNotBlackjackUserBlackjack() {
		Card ace = new Card("1S", 11);
		Card ten = new Card("10S", 10);
		Card seven = new Card("7S", 7);
				
		testGame.getUser().emptyHand();
		testGame.getDealer().emptyHand();
		//user has BJ
		testGame.getSingleUserHand(0).addCardToHand(ace);
		testGame.getSingleUserHand(0).addCardToHand(ten);
		//delaer has 18
		testGame.getDealerHands().get(0).addCardToHand(ace);
		testGame.getDealerHands().get(0).addCardToHand(seven);
				
		testGame.handleInsurancePress();
		testGame.handleStandPress();
				
		assertEquals(110, testGame.getUserNumberOfChips());
		assertEquals(true, testGame.isHandWon(0));
	}
}
