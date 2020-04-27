package blackjack;

public class Card {
	//Shorthand for cards in file names for images is: 
	//1-10, J = 11, Q = 12, K = 13 followed by H = hearts, S = spades, C = clubs, D = diamonds
	//ex. 1S = ace/spades, 4D = 4/diamonds, 13H = king/hearts, etc
	private String cardImageFilePath; 
	//Score values of each card:
	//A = 11 (by default, score changed to 1 in Player class as needed), 2-10 = number on card, J/Q/K = 10
	private int value; 
	
	public Card(String cardNumberAndSuit, int value){
		cardImageFilePath = "/" + cardNumberAndSuit + ".png";
		this.value = value;
	}
	
	//----------------------------Setters and Getters----------------------------//
	public int getValue() {
		return this.value;
	}
	public String getCardImageFilePath() {
		return this.cardImageFilePath;
	}
}
