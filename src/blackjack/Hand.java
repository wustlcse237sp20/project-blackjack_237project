package blackjack;

import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> cardsInHand;
	private int score;
	private boolean containsBlackjack;
	
	public Hand(){
		cardsInHand = new ArrayList<Card>();
	}
	
	/**
	 * Adds the supplied card to the hand, updating the flag denoting that the hand contains an ace as needed
	 * @param cardToAdd
	 */
	public void addCardToHand(Card cardToAdd) {
		cardsInHand.add(cardToAdd);
		//TODO: Implement lowering score if an ace is to be treated as a 1 and changing the blackjack flag if needed
		
		score += cardToAdd.value;
	}
	public void emptyCardsInHand() {
		cardsInHand.clear();
		score = 0;
	}
	public boolean doesHandHaveBlackjack() {
		return containsBlackjack;
	}
	public ArrayList<Card> getCardsInHand() {
		return this.cardsInHand;
	}
	public void removeCardFromHand(int cardToRemove) {
		score -= cardsInHand.get(cardToRemove).value;
		cardsInHand.remove(cardToRemove);
	}
	public int getScore() {
		return score;
	}
}
