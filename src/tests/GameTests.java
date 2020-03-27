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
        protected int askForNumberOfDecksToUse() {
        	return 1;
        }
        @Override
        protected boolean askToPlayNewHand() {
        	return false;
        }
    }
	private MockedBlackjack testGame;
	
	@BeforeEach
	void setupGameInstance() {
		testGame = new MockedBlackjack();
	}
	
	
	@Test
	void testBustOnHit() {
		JButton mockButton = new JButton();
		ActionEvent mockPressHitButton = new ActionEvent(mockButton, ActionEvent.ACTION_PERFORMED,"Hit");
		testGame.user.score = 21;
		testGame.actionPerformed(mockPressHitButton);
		assertEquals(testGame.handOver, true);
		assertEquals(testGame.handWon, false);
	}
}
