package blackjack;

public class Card {
	// 1-10, J = 11, Q = 12, K = 13 dollowed by H = hearts, S = spades, C = clubs, D = diamonds
	public String cardImageFilePath; 
	public int value; //the score value of the card
	
	Card(String cardNumberAndSuit){
		cardImageFilePath = "/" + cardNumberAndSuit + ".png"; //ex. 1S = ace/spades, 4D = 4/diamonds, 13H = king/hearts, etc
	}
}
