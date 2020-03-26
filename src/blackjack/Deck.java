package blackjack;

public class Deck {
	private int numberOfDecks;
	
	public void shuffle() {
		
	}
	
	public void dealOutHands() {
		
	}
	
	public boolean setNumberOfDecks(int numberDecksToUse) {
		if(numberDecksToUse < 1 || numberDecksToUse > 8) {
			numberOfDecks = 1;
			return false;
		} else {
			numberOfDecks = numberDecksToUse;
			return true;
		}
	}
	
	public int getNumberOfDecks() {
		return numberOfDecks;
	}
	
}
