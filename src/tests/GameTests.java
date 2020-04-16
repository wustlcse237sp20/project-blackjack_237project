package tests;
import blackjack.*;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTests{
	private class MockedBlackjack extends Blackjack {
		@Override
        protected void setDeckSize() {
			deck = new Deck(1);
        }
		@Override
        protected void setChipAmount() {
			getUser().setChipAmount(100);
        }
		@Override
        protected void setBetAmount() {
			getUser().setBet(100);
			getUser().subtractChips(100);
        }
        @Override
        protected boolean startNewHand() {
        	return false;
        }
    }
	private GUI userInterface;
	private MockedBlackjack testGame;
	
	@BeforeEach
	void setupGameInstance() {
		testGame = new MockedBlackjack();
		userInterface = new GUI(700, 700, testGame);
	}
	
	
	@Test
	void testBustOnHit() {
		JButton mockButton = new JButton();
		ActionEvent mockPressHitButton = new ActionEvent(mockButton, ActionEvent.ACTION_PERFORMED,"Hit");
		while(testGame.getUser().getPlayerScore() < 21) {
			userInterface.actionPerformed(mockPressHitButton);
		}
		assertEquals(testGame.isHandOver(), true);
		assertEquals(testGame.isHandWon(), false);
		assertEquals(testGame.isHandPush(), false);
	}
	
	@Test
	void testPushByScore() {
		JButton mockButton = new JButton();
		ActionEvent mockPressStandButton = new ActionEvent(mockButton, ActionEvent.ACTION_PERFORMED,"Stand");
		
		Hand pushHand = new Hand();		
		for(int i = 0; i < 2; i++) {
			pushHand.addCardToHand(new Card("10S", 10));
		}
		
		testGame.getUser().emptyHand();
		testGame.getDealer().emptyHand();
		for(Card cardInHand : pushHand.getCardsInHand()) {
			testGame.getUser().addCardToHand(cardInHand);
			testGame.getDealer().addCardToHand(cardInHand);
		}
		userInterface.actionPerformed(mockPressStandButton);
		assertEquals(testGame.isHandOver(), true);
		assertEquals(testGame.isHandWon(), false);
		assertEquals(testGame.isHandPush(), true);
	}
	
	@Test
	void testPushByBlackjack() {
		JButton mockButton = new JButton();
		ActionEvent mockPressStandButton = new ActionEvent(mockButton, ActionEvent.ACTION_PERFORMED,"Stand");
		
		Hand blackjackHand = new Hand();		
		blackjackHand.addCardToHand(new Card("1S", 11));
		blackjackHand.addCardToHand(new Card("13H", 10));
		
		testGame.getUser().emptyHand();
		testGame.getDealer().emptyHand();
		for(Card cardInHand : blackjackHand.getCardsInHand()) {
			testGame.getUser().addCardToHand(cardInHand);
			testGame.getDealer().addCardToHand(cardInHand);
		}
		userInterface.actionPerformed(mockPressStandButton);
		assertEquals(testGame.isHandOver(), true);
		assertEquals(testGame.isHandWon(), false);
		assertEquals(testGame.isHandPush(), true);
	}
	
	@Test
	void testWinByScore() {
		JButton mockButton = new JButton();
		ActionEvent mockPressStandButton = new ActionEvent(mockButton, ActionEvent.ACTION_PERFORMED,"Stand");
		
		Hand winningHand = new Hand();
		Hand losingHand = new Hand();		
		for(int i = 0; i < 2; i++) {
			losingHand.addCardToHand(new Card("9H", 9)); //losing hand is 18
		}
		winningHand.addCardToHand(new Card("9S", 9)); //winning hand is 21
		winningHand.addCardToHand(new Card("9H", 9));
		winningHand.addCardToHand(new Card("3D", 3));
		
		//Test user score greater than dealer
		testGame.getUser().emptyHand();
		testGame.getDealer().emptyHand();
		for(Card cardInHand : winningHand.getCardsInHand()) {
			testGame.getUser().addCardToHand(cardInHand);
		}
		for(Card cardInHand : losingHand.getCardsInHand()) {
			testGame.getDealer().addCardToHand(cardInHand);
		}
		userInterface.actionPerformed(mockPressStandButton);
		assertEquals(testGame.isHandOver(), true);
		assertEquals(testGame.isHandWon(), true);
		assertEquals(testGame.isHandPush(), false);
		
	}
	
	@Test
	void testWinByBlackjack() {
		JButton mockButton = new JButton();
		ActionEvent mockPressStandButton = new ActionEvent(mockButton, ActionEvent.ACTION_PERFORMED,"Stand");
		
		Hand losingHand = new Hand();
		Hand blackjackHand = new Hand();		
		
		losingHand.addCardToHand(new Card("9S", 9)); //losing hand is 21
		losingHand.addCardToHand(new Card("9H", 9));
		losingHand.addCardToHand(new Card("3D", 3));
		blackjackHand.addCardToHand(new Card("1S", 11)); //blackjack is K and A
		blackjackHand.addCardToHand(new Card("13H", 10));
		
		//test user has blackjack while dealer has 21
		testGame.getUser().emptyHand();
		testGame.getDealer().emptyHand();
		for(Card cardInHand : losingHand.getCardsInHand()) {
			testGame.getDealer().addCardToHand(cardInHand);
		}
		for(Card cardInHand : blackjackHand.getCardsInHand()) {
			testGame.getUser().addCardToHand(cardInHand);
		}
		userInterface.actionPerformed(mockPressStandButton);
		assertEquals(testGame.isHandOver(), true);
		assertEquals(testGame.isHandWon(), true);
		assertEquals(testGame.isHandPush(), false);
	}
	
	@Test
	void testLossByScore() {
		JButton mockButton = new JButton();
		ActionEvent mockPressStandButton = new ActionEvent(mockButton, ActionEvent.ACTION_PERFORMED,"Stand");
		
		Hand winningHand = new Hand();
		Hand losingHand = new Hand();		
		winningHand.addCardToHand(new Card("9S", 9)); //winning hand is 21
		winningHand.addCardToHand(new Card("3H", 3));
		winningHand.addCardToHand(new Card("9D", 9));
		for(int i = 0; i < 2; i++) { 
			losingHand.addCardToHand(new Card("9H", 9)); //losing hand is 18
		}
		
		//test dealer has higher score than user
		testGame.getUser().emptyHand();
		testGame.getDealer().emptyHand();
		for(Card cardInHand : winningHand.getCardsInHand()) {
			testGame.getDealer().addCardToHand(cardInHand);
		}
		for(Card cardInHand : losingHand.getCardsInHand()) {
			testGame.getUser().addCardToHand(cardInHand);
		}
		userInterface.actionPerformed(mockPressStandButton);
		assertEquals(testGame.isHandOver(), true);
		assertEquals(testGame.isHandWon(), false);
		assertEquals(testGame.isHandPush(), false);
	}
	
	@Test
	void testLossByBlackjack() {
		JButton mockButton = new JButton();
		ActionEvent mockPressStandButton = new ActionEvent(mockButton, ActionEvent.ACTION_PERFORMED,"Stand");
		
		Hand losingHand = new Hand();
		Hand blackjackHand = new Hand();		
		losingHand.addCardToHand(new Card("9S", 9)); //losing hand is 21
		losingHand.addCardToHand(new Card("9H", 9));
		losingHand.addCardToHand(new Card("3D", 3));
		blackjackHand.addCardToHand(new Card("1S", 11)); //blackjack is A K
		blackjackHand.addCardToHand(new Card("13H", 10));
		
		
		//test dealer has blackjack while user has 21
		testGame.getUser().emptyHand();
		testGame.getDealer().emptyHand();
		for(Card cardInHand : losingHand.getCardsInHand()) {
			testGame.getUser().addCardToHand(cardInHand);
		}
		for(Card cardInHand : blackjackHand.getCardsInHand()) {
			testGame.getDealer().addCardToHand(cardInHand);
		}
		userInterface.actionPerformed(mockPressStandButton);
		assertEquals(testGame.isHandOver(), true);
		assertEquals(testGame.isHandWon(), false);
		assertEquals(testGame.isHandPush(), false);
	}
}