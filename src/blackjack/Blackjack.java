package blackjack;

import java.awt.EventQueue;
import java.util.ArrayList;

public class Blackjack {
	private GUI userInterface;
	private int frameHeight = 800;
	private int frameWidth = 800;
	
	protected Deck deck;
	private int numberOfPlayers = 1; //number of players (not including the dealer)
	private ArrayList<Player> players = new ArrayList<Player>(); //first player in list is the dealer, second is the user, all rest are computer controlled
	private boolean handOver = false;
	private boolean handWon = false;
	private boolean handPushed = false;

	/**
	 * Launches the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Blackjack window = new Blackjack();
					window.userInterface.showUserInterface();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Sets up the application window and runs the game
	 */
	public Blackjack() {
		userInterface = new GUI(frameHeight, frameWidth, this);
		for(int i = 0; i < numberOfPlayers + 1; i++) {
			players.add(new Player());
		}
		setDeckSize();
		setChipAmount();
		userInterface.initializeGUI();
		playAHand();
	}
	
	protected void setDeckSize() {
		int numberOfSingleDecksToUse = userInterface.askForNumberOfDecksToUse();
		while(numberOfSingleDecksToUse < 1 || numberOfSingleDecksToUse > 8) {
			userInterface.showDeckSizeError();
			numberOfSingleDecksToUse = userInterface.askForNumberOfDecksToUse();
		}
		deck = new Deck(numberOfSingleDecksToUse);
	}
	
	protected void setChipAmount() {
		int numberOfChipsToStartWith = userInterface.askForNumberOfChips();
		while(numberOfChipsToStartWith < 100 || numberOfChipsToStartWith > 999999) {
			userInterface.showChipNumberError();
			numberOfChipsToStartWith = userInterface.askForNumberOfChips();
		}
		getUser().setChipAmount(numberOfChipsToStartWith);
	}
	
	/**
	 * Deals out a hand to the players and displays the appropriate cards on the GUI
	 */
	private void playAHand() {
		handOver = false;
		handWon = false;
		if(getUser().getNumberOfChips() == 0) {
			userInterface.showChipsGoneMessage();
			setChipAmount();
		}
		setBetAmount();
		deck.dealOutHands(players);
		userInterface.displayHandsOnFrame(true);
	}
	protected void setBetAmount() {
		int betAmount = userInterface.askForBet();
		while(betAmount < 1 || betAmount > getUser().getNumberOfChips()) {
			userInterface.showBetAmountError();
			betAmount = userInterface.askForBet();
		}
		getUser().setBet(betAmount);
		getUser().subtractChips(betAmount);
	}
	
	/**
	 * Determines based on the player's and dealers score/hand if the player won the hand
	 */
	private void determineWinnerOfHand() {
		if(getUserScore() > getDealerScore() || getDealerScore() > 21) { //if dealer busts or has lower score
			handWon = true;
			if(getUser().doesPlayerHaveBlackjack()) {
				getUser().addChips(2.5*getUser().getBet());
			} else {
				getUser().addChips(2*getUser().getBet());
			}
		}
		if(getUserScore() < getDealerScore()) {
			handWon = false;
		}
		if(getUserScore() == getDealerScore()) { //if dealer and player have same score
			if(getDealer().doesPlayerHaveBlackjack() && !getUser().doesPlayerHaveBlackjack()) { //player loses if dealer has blackjack but player doesnt
				handWon = false;
			} else if(!getDealer().doesPlayerHaveBlackjack() && getUser().doesPlayerHaveBlackjack()){ //player wins if they have blackjack but dealer doesn't
				handWon = true;
				getUser().addChips(2.5*getUser().getBet());
			} else {
				handPushed = true;
				getUser().addChips(getUser().getBet());
			}
		}
	}
	
	public void handleHitPress() {
		getUser().hit(deck);
		userInterface.displayHandsOnFrame(true);
		if(getUserScore() > 21) {
			handWon = false;
			handOver = true;
			userInterface.displayHandsOnFrame(false);
			finishHand();
			if(startNewHand()) {
				playAHand();
			}
		}
	}
	
	public void handleStandPress() {
		getDealer().playDealersHand();
		determineWinnerOfHand();
		handOver = true;
		userInterface.displayHandsOnFrame(false);
		finishHand();
		if(startNewHand()) {
			playAHand();
		}
	}
	
	/**
	 * Determines if a new hand should be played based on a user prompt (or direct input via method override in JUnit tests)
	 * @return true if a new hand should be played
	 */
	protected boolean startNewHand() {
		return userInterface.askToPlayNewHand();
	}
	
	/**
	 * Displays a notification in the center of the GUI norifying the player the outcome of the hand
	 */
	private void finishHand() {
		userInterface.createTextLabel(170, 60,"Score: " + String.valueOf(getDealerScore()));
		if(handWon) {
			//display win text
			userInterface.createTextLabel(frameHeight/2-15, 65, "Hand Won");
		} else {
			if(handPushed) {
				//display draw text
				userInterface.createTextLabel(frameHeight/2-15, 85, "Hand Pushed");
			} else {
				//diplay draw text
				userInterface.createTextLabel(frameHeight/2-15, 65, "Hand Lost");
			}
		}
	}
	
	public Player getDealer() {
		return players.get(0);
	}
	public Player getUser() {
		return players.get(1);
	}
	public int getDealerScore(){
		return players.get(0).getPlayerScore();
	}
	public int getUserScore(){
		return players.get(1).getPlayerScore();
	}
	public boolean isHandOver() {
		return handOver;
	}
	public boolean isHandWon() {
		return handWon;
	}
	public boolean isHandPush() {
		return handPushed;
	}
}
