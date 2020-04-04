package blackjack;

import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> cardsInHand;
	private boolean containsAce;
	
	public Hand(){
		cardsInHand = new ArrayList<Card>();
	}
	
	/**
	 * Adds the supplied card to the hand, updating the flag denoting that the hand contains an ace as needed
	 * @param cardToAdd
	 */
	public void addCardToHand(Card cardToAdd) {
		cardsInHand.add(cardToAdd);
		if(cardToAdd.value == 11) { //default value for an ace is 11, score lowered to 1 in player class if needed
			containsAce = true;
		}
	}
	public void emptyCardsInHand() {
		cardsInHand.clear();
	}
	
	public ArrayList<Card> getCardsInHand() {
		return this.cardsInHand;
	}
	public boolean containsAce() {
		return this.containsAce;
	}
}
