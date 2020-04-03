package blackjack;

import java.util.ArrayList;
import java.util.ListIterator;

public class Deck {
	private int numberOfDecks;
	private ArrayList<Card> cardsInADeck;
	public  ArrayList<Card> allCardsInGame;
	public Deck(){
		this.cardsInADeck = new ArrayList<Card>();
		this.allCardsInGame = new ArrayList<Card>();
		for(int j=0;j<13;j++) {
			String card = "H" + j; 
			cardsInADeck.add(new Card(card));
		}
		for(int j=0;j<13;j++) {
			String card = "C" + j; 
			cardsInADeck.add(new Card(card));
		}
		for(int j=0;j<13;j++) {
			String card = "S" + j; 
			cardsInADeck.add(new Card(card));
		}
		for(int j=0;j<13;j++) {
			String card = "D" + j; 
			cardsInADeck.add(new Card(card));
		}
		for(Card card:cardsInADeck) {
			allCardsInGame.add(card);
		}
	}
	
	public void shuffle() {
		java.util.Collections.shuffle(this.allCardsInGame);
	}
	
	public void dealOutHands() {
		
	}
	
	public boolean setNumberOfDecks(int numberDecksToUse) {
		if(numberDecksToUse < 1 || numberDecksToUse > 8) {
			numberOfDecks = 1;
			return false;
		} else {
			numberOfDecks = numberDecksToUse;
			for(int i = 0;i<numberOfDecks-1;i++) {
				for(Card card:cardsInADeck) {
					allCardsInGame.add(card);
				}
			}
			return true;
		}
	}
	
	public int getNumberOfDecks() {
		return numberOfDecks;
	}
	
}
