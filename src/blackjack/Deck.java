package blackjack;

import java.util.ArrayList;

public class Deck {
	private int numberOfSingleDecksToUse;
	private ArrayList<Card> cardsInTheDeck;
	private ArrayList<Card> cardsDeltToPlayers = new ArrayList<Card>();
	private int count;
	
	/**
	 * Creates a new deck of cards using the supplied number of single 52 card decks
	 * @param numberOfSingleDecksToUse
	 */
	public Deck(int numberOfSingleDecksToUse){
		this.numberOfSingleDecksToUse = numberOfSingleDecksToUse;
		this.cardsInTheDeck = new ArrayList<Card>();
		for(int i = 0; i < this.numberOfSingleDecksToUse; i++) {
			addCardsOfSingleSuitToDeck("H");
			addCardsOfSingleSuitToDeck("C");
			addCardsOfSingleSuitToDeck("S");
			addCardsOfSingleSuitToDeck("D");
			
		}
	}
	/**
	 * Adds the set of cards A to K for a single supplied suit to the deck
	 * H = hearts, C = clubs, S = spades, D = diamonds
	 * @param suitOfCardsToAdd
	 */
	private void addCardsOfSingleSuitToDeck(String suitOfCardsToAdd) {
		for(int j = 1; j < 14; j++) {
			String cardNumberAndSuit = j + suitOfCardsToAdd;
			int cardValue;
			if (j == 1) {
				cardValue = 11; //default value for ace is 11, changing score to 1 is handled in Player class
			} else if (j < 11) {
				cardValue = j;
			} else {
				cardValue = 10;
			}
			cardsInTheDeck.add(new Card(cardNumberAndSuit, cardValue));
		}
	}
	public void shuffle() {
		if(!cardsDeltToPlayers.isEmpty()) {
			for(Card cardToReshuffle : cardsDeltToPlayers) {
				cardsInTheDeck.add(cardToReshuffle);
			}
		}
		java.util.Collections.shuffle(cardsInTheDeck);
		this.count = 0;
	}
	/**
	 * If more than half the deck has been delt, the deck is reshuffled, then two cards are delt to each of the players in 
	 * the supplied ArrayList of players. 
	 * @param players
	 */
	public void dealOutHands(ArrayList<Player> players) {
		if(cardsInTheDeck.size() < cardsDeltToPlayers.size()) { //reshuffle the deck when more than half the deck has been delt
			shuffle();
		}
		for (Player player : players) {
			player.emptyHand();
			for(int i = 0; i < 2; i++) {
				dealNextCardToHand(player.getHands().get(0));
			}
		}
	}
	/**
	 * Gives the next card in the deck to the supplied Player
	 * @param playerToDealTo
	 */
	public void dealNextCardToHand(Hand handToDealTo) {
		handToDealTo.addCardToHand(cardsInTheDeck.get(0));
		cardsDeltToPlayers.add(cardsInTheDeck.get(0));
		int cardValue = cardsInTheDeck.get(0).getValue();
		if (cardValue >= 2 && cardValue <= 6) {
			count++;
		}
		else if (cardValue >= 10) {
			count--;
		}
		cardsInTheDeck.remove(0);
	}
	
	//----------------------------Setters and Getters----------------------------//
	public ArrayList<Card> getCardsInTheDeck(){
		return this.cardsInTheDeck;
	}
	public ArrayList<Card> getDeltCards(){
		return this.cardsDeltToPlayers;
	}
	public int getCount() {
		return this.count;
	}
}
