package blackjack;

import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> cardsInHand;
	private int score;
	private int aces = 0;
	
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
		if (cardToAdd.getValue() == 11) {
			aces++;
		}
		score += cardToAdd.getValue();
	}
	public void emptyCardsInHand() {
		cardsInHand.clear();
		aces = 0;
		score = 0;
	}
	public boolean doesHandHaveBlackjack() {
		if (getScore() == 21 && cardsInHand.size() == 2) {
			return true;
		}
		return false;
	}
	public ArrayList<Card> getCardsInHand() {
		return this.cardsInHand;
	}
	public void removeCardFromHand(int cardToRemove) {
		int valueOfCardToSubtract = cardsInHand.get(cardToRemove).getValue();
		score -= valueOfCardToSubtract;
		if(valueOfCardToSubtract == 11) {
			aces--;
		}
		cardsInHand.remove(cardToRemove);
	}
	public int getScore() {
		while (score > 21 && aces > 0) {
			score -= 10;
			aces--;
		}
		return score;
	}
	public boolean doesHandContainPair() {
		if(cardsInHand.get(0).getValue() == cardsInHand.get(1).getValue()) {
			return true;
		} else {
			return false;
		}
	}
}
