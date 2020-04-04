package tests;
import blackjack.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class DeckTests {
	
	@Test
	void testNewDeck() {
		for(int i = 1; i < 8; i++) {
			Deck deck = new Deck(i);
			if(i > 0 && i < 9)
				assertEquals(52*i, deck.getCardsInTheDeck().size());
			else
				assertEquals(52, deck.getCardsInTheDeck().size());
		}
	}
	
	
	@Test
	void testShuffle(){
		for(int i = 1; i < 8; i++) {
			Deck deck = new Deck(i);
			ArrayList<Card> copy = (ArrayList<Card>) deck.getCardsInTheDeck().clone();
			deck.shuffle();
			assertNotEquals(copy, deck.getCardsInTheDeck());
			
		}
	}
	
	@Test
	void testDealingOutHands() {
		Deck deck = new Deck(8);
		ArrayList<Player> testPlayers = new ArrayList<Player>();
		for(int i = 0; i < 6; i++) {
			testPlayers.add(new Player());
		}
		deck.dealOutHands(testPlayers);
		for(Player player: testPlayers) {
			assertEquals(player.getHand().size(), 2);
			assertEquals(deck.getCardsInTheDeck().size(), 8*52-6*2);
			for(Card deltCard : player.getHand()) {
				assertEquals(deck.getCardsInTheDeck().contains(deltCard), false);
			}
		}
	}
	
	@Test
	void testPreservationOfDeltCards() {
		Deck deck = new Deck(8);
		ArrayList<Player> testPlayers = new ArrayList<Player>();
		for(int i = 0; i < 6; i++) {
			testPlayers.add(new Player());
		}
		deck.dealOutHands(testPlayers);
		for(int i = 0; i < 2; i++) {
			deck.dealNextCardToPlayer(testPlayers.get(1));
		}
		for(Player player : testPlayers) {
			for(Card cardInHand : player.getHand()) {
				assertEquals(deck.getDeltCards().contains(cardInHand), true);
				assertEquals(deck.getCardsInTheDeck().contains(cardInHand), false);
			}
		}
	}
	
	@Test
	void testDealingACardToAPlayer() {
		Deck deck = new Deck(8);
		Player testPlayer = new Player();
		for(int i = 0; i < 2; i++) {
			deck.dealNextCardToPlayer(testPlayer);
		}
		assertEquals(testPlayer.getHand(), deck.getDeltCards());
		for(Card deltCard : deck.getDeltCards()) {
			assertEquals(deck.getCardsInTheDeck().contains(deltCard), false);
		}
	}
	
	@Test
	void testReshuffleDeltCardsWhenNeeded() {
		for(int i = 1; i < 9; i++) {
			Deck deck = new Deck(i);
			ArrayList<Card> fullDeck = (ArrayList<Card>) deck.getCardsInTheDeck().clone();
			Player testPlayer = new Player();
			for(int j = 0; j < (i*52)/2+1; j++) {
				deck.dealNextCardToPlayer(testPlayer);
			}
			deck.shuffle();
			assertEquals(deck.getCardsInTheDeck().size(), i*52);
			for(Card card : fullDeck) {
				assertEquals(deck.getCardsInTheDeck().contains(card), true);
			}
		}
	}
}
