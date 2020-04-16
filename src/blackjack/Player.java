package blackjack;

import java.util.ArrayList;

public class Player {
	private Hand hand;
	private int score;
	private boolean handIsBlackjack;
	private double chips;
	private double bet;
	
	public Player(){
		hand = new Hand();
	}
	
	/**
	 * Performs a hit, dealing a card from the supplied deck to the player
	 * @param deckToDrawFrom
	 */
	public void hit(Deck deckToDrawFrom) {
		deckToDrawFrom.dealNextCardToPlayer(this);
	}
	/**
	 * Adds a card to the players Hand and increases their score based on its value
	 * @param cardToAdd
	 */
	public void addCardToHand(Card cardToAdd) {
		hand.addCardToHand(cardToAdd);
		if(hand.containsAce()) {
			checkIfPlayerHasBlackjack();
			//TODO: handle changing score of aces (subtract 10 from the players score, 
			//but don't change the value stored in the card object)
			score += cardToAdd.value; //replace this with neccessary code
		} else {
			score += cardToAdd.value;
		}
	}
	public void emptyHand() {
		hand.emptyCardsInHand();
		score = 0;
	}
	
	//TODO: add handling of player being delt blackjack to update boolean
	public void checkIfPlayerHasBlackjack() {
	}
	//TODO: add handling of hitting for dealer's hand
	public void playDealersHand() {
	}
	
	public void subtractChips(int numberOfChipsToSubtract) {
		chips -= numberOfChipsToSubtract;
	}
	
	public void addChips(double numberOfChipsToAdd) {
		chips += numberOfChipsToAdd;
	}
	
	public ArrayList<Card> getHand(){
		return hand.getCardsInHand();
	}
	public int getPlayerScore() {
		return this.score;
	}
	public boolean doesPlayerHaveBlackjack() {
		return this.handIsBlackjack;
	}
	public double getNumberOfChips() {
		return chips;
	}
	public void setChipAmount(int chips) {
		this.chips = chips;
	}
	public double getBet() {
		return bet;
	}
	public void setBet(double bet) {
		this.bet = bet;
	}
}
