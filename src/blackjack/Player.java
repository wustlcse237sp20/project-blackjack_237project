package blackjack;

import java.util.ArrayList;

public class Player {
	private ArrayList<Hand> hands;
	private double chips;
	private double bet;
	
	public Player(){
		hands = new ArrayList<Hand>();
		hands.add(new Hand());
	}
	
	/**
	 * Performs a hit, dealing a card from the supplied deck to the player
	 * @param deckToDrawFrom
	 */
	public void hit(Deck deckToDrawFrom, int handToHitOn) {
		deckToDrawFrom.dealNextCardToHand(hands.get(handToHitOn));
	}
	public void emptyHand() {
		if(hands.size() == 2) {
			hands.remove(1);
		}
		hands.get(0).emptyCardsInHand();
		
		
	}
	
	public void subtractChips(int numberOfChipsToSubtract) {
		chips -= numberOfChipsToSubtract;
	}
	public void addChips(double numberOfChipsToAdd) {
		chips += numberOfChipsToAdd;
	}
	public void splitHands() {
		hands.add(new Hand());
		hands.get(1).addCardToHand(hands.get(0).getCardsInHand().get(1)); //copy the second card in the first hand to the first card in the second hand
		hands.get(0).removeCardFromHand(1); //remove the copied card from the original hand
	}
	
	public void hitBasedOnRules(Player dealer, Deck deck, int type) {
	
		
		
		
		
		switch(type) {
			case 1:
				System.out.println(this.getSingleHand(0).getScore());
				System.out.println("start");
				if(this.getSingleHand(0).getScore()<15) {
					System.out.println(this.getSingleHand(0).getScore());
					System.out.println("after");
					this.hit(deck, 0);
					System.out.println(this.getSingleHand(0).getScore());
					System.out.println("done");
					break;
				}
				
				
				
				
				
			case 2:
				if(((this.getSingleHand(0).getScore()<=16)&&(dealer.getCardsInSingleHand(0).get(0).getValue()==10||dealer.getCardsInSingleHand(0).get(0).getValue()==11))||(this.getSingleHand(0).getScore()<=11)) {
					this.hit(deck,  0);
					break;
				}
			case 3:
				if(this.getSingleHand(0).getScore()<=11) {
					this.hit(deck, 0);
					break;
				}
			case 4:
				if((this.getSingleHand(0).getScore() > 11&&this.getSingleHand(0).getScore()<16)&&(this.getCardsInSingleHand(0).get(0).getValue()==11||this.getCardsInSingleHand(0).get(1).getValue()==11)||(this.getSingleHand(0).getScore()<=11)) {
					this.hit(deck,  0);
					break;
				}
			case 5:
				if(this.getSingleHand(0).getScore()<=16) {
					this.hit(deck, 0);
					break;
				}
				
				
		}
	}
	
	//----------------------------Setters and Getters----------------------------//
	public ArrayList<Hand> getHands(){
		return hands;
	}
	public Hand getSingleHand(int handToGet){
		return hands.get(handToGet);
	}
	public ArrayList<Card> getCardsInSingleHand(int handToGet){
		return hands.get(handToGet).getCardsInHand();
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
