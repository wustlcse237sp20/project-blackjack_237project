package blackjack;

import java.util.ArrayList;

public class Player {
	public ArrayList<Card> hand;
	public int score;
	
	public Player(){
		hand = new ArrayList<Card>();
		

	}
	
	public void hit(Card card) {
		hand.add(card);
	}
	
	public void stand() {
		//do nothing
	}
}
