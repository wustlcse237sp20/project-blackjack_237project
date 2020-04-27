package blackjack;

import java.awt.EventQueue;
import java.util.ArrayList;

public class Blackjack {
	public GUI userInterface;
	private int frameHeight = 800;
	private int frameWidth = 800;
	
	protected Deck deck;
	private int numberOfPlayers = 1; //number of players (not including the dealer)
	private ArrayList<Player> players = new ArrayList<Player>(); //first player in list is the dealer, second is the user, all rest are computer controlled
	private boolean handsOver = false;
	private ArrayList<Boolean> handsWon;
	private ArrayList<Boolean> handsPushed;

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
		handsWon = new ArrayList<Boolean>();
		handsPushed = new ArrayList<Boolean>();
		userInterface = new GUI(frameHeight, frameWidth, this);
		for(int i = 0; i < numberOfPlayers + 1; i++) {
			players.add(new Player());
		}
		setDeckSize();
		deck.shuffle();
		setChipAmount();
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
		handsOver = false;
		handsWon.clear();
		handsPushed.clear();
		if(getUserNumberOfChips() == 0) {
			userInterface.showChipsGoneMessage();
			setChipAmount();
		}
		setBetAmount();
		deck.dealOutHands(players);
		userInterface.displayHandsOnFrame(true);
	}
	protected void setBetAmount() {
		int betAmount = userInterface.askForBet();
		while(betAmount < 1 || betAmount > getUserNumberOfChips()) {
			userInterface.showBetAmountError();
			betAmount = userInterface.askForBet();
		}
		getUser().setBet(betAmount);
		getUser().subtractChips(betAmount);
	}
	
	public void handleHitPress() {
		if(userInterface.getControllingHandNumber() == 0) {
			getUser().hit(deck, 0);
			userInterface.displayHandsOnFrame(true);
			if(getUserHandScore(getUserHands().get(0)) > 21) {
				playDealersHand();
				determineWinnerOfHand();
				userInterface.displayHandsOnFrame(false);
				finishHand();
				if(startNewHand()) {
					playAHand();
				}
			}
		} else {
			getUser().hit(deck, userInterface.getControllingHandNumber()-1);
			userInterface.displayHandsOnFrame(true);
			if(getUserHandScore(getUserHands().get(userInterface.getControllingHandNumber()-1)) > 21) {
				if(userInterface.getControllingHandNumber() == 2) {
					userInterface.incrementControllingHandNumber();
					playDealersHand();
					determineWinnerOfHand();
					userInterface.displayHandsOnFrame(false);
					finishHand();
					if(startNewHand()) {
						playAHand();
					}
				} else {
					userInterface.incrementControllingHandNumber();
					userInterface.displayHandsOnFrame(true);
				}
			}
		}
	}	
	public void handleStandPress() {
		if(userInterface.getControllingHandNumber() == 1) {
			userInterface.incrementControllingHandNumber();
			userInterface.displayHandsOnFrame(true);
		} else {
			if(userInterface.getControllingHandNumber() == 2) {
				userInterface.incrementControllingHandNumber(); //dealer didnt hit on a thirteen; occasionally has score reset
			}
			playDealersHand();
			determineWinnerOfHand();
			userInterface.displayHandsOnFrame(false);
			finishHand();
			if(startNewHand()) {
				playAHand();
			}
		}
	}
	public void handleSplitPress() {
		getUser().splitHands();
		getUser().hit(deck, 0);
		getUser().hit(deck, 1);
		getUser().subtractChips((int)getUser().getBet());
		userInterface.displayHandsOnFrame(true);
	}
	public void handleDoubleDownPress() {
		//TODO:Implement double down procedure (double users bet and hit once, followed by standing)
		getUser().subtractChips((int)getUser().getBet());
		getUser().setBet(2*(int)getUser().getBet());
		getUser().hit(deck,  0);
		//userInterface.displayHandsOnFrame(true);
		handleStandPress();
		
	}
	
	/**
	 * Determines based on the player's and dealers score/hand if the player won the hand and pays out bets accordingly
	 */
	private void determineWinnerOfHand() {
		for(Hand handToCheck : getUser().getHands()) {
			if(handToCheck.getScore() <= 21) { //if user doesnt bust on hand
				if(handToCheck.getScore() > getDealerScore() || getDealerScore() > 21) { //if dealer busts or has lower score
					handsWon.add(true);
					handsPushed.add(false);
					if(handToCheck.doesHandHaveBlackjack()) { //check for BJ chip payout
						getUser().addChips(2.5*getUserBet());
					} else {
						getUser().addChips(2*getUserBet());
					}
				}
				if(handToCheck.getScore() < getDealerScore() && getDealerScore() <= 21) {
					handsWon.add(false);
					handsPushed.add(false);
				}
				if(handToCheck.getScore() == getDealerScore()) { //if dealer and player have same score
					if(getDealerHands().get(0).doesHandHaveBlackjack() && !handToCheck.doesHandHaveBlackjack()) { //player loses if dealer has blackjack but player doesnt
						handsWon.add(false);
						handsPushed.add(false);
					} else if(!getDealerHands().get(0).doesHandHaveBlackjack() && handToCheck.doesHandHaveBlackjack()){ //player wins if they have blackjack but dealer doesn't
						handsWon.add(true);
						handsPushed.add(false);
						getUser().addChips(2.5*getUserBet());
					} else {
						handsWon.add(false);
						handsPushed.add(true);
						getUser().addChips(getUserBet());
					}
				}
			} else { //user busted hand, auto loss
				handsWon.add(false);
				handsPushed.add(false);
			}
		}
		handsOver = true;
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
		userInterface.createTextLabel(frameWidth/2 - 30, 170, 60,"Score: " + String.valueOf(getDealerScore()));
		if(handsWon.size() > 1) {
			for(int i = 0; i < handsWon.size(); i++) {
				if(handsWon.get(i)) {
					//display win text
					userInterface.createTextLabel(frameWidth/2 - 125+i*175, frameHeight-365, 65, "Hand Won");
				} else {
					if(handsPushed.get(i)) {
						//display draw text
						userInterface.createTextLabel(frameWidth/2 - 125+i*175, frameHeight-365, 85, "Hand Pushed");
					} else {
						//diplay loss text
						userInterface.createTextLabel(frameWidth/2 - 125+i*175, frameHeight-365, 65, "Hand Lost");
					}
				}
			}
		} else {
			if(handsWon.get(0)) {
				//display win text
				userInterface.createTextLabel(frameWidth/2 - 32, frameHeight-365, 65, "Hand Won");
			} else {
				if(handsPushed.get(0)) {
					//display draw text
					userInterface.createTextLabel(frameWidth/2 - 42, frameHeight-365, 85, "Hand Pushed");
				} else {
					//diplay loss text
					userInterface.createTextLabel(frameWidth/2 - 32, frameHeight-365, 65, "Hand Lost");
				}
			}
		}
	}
	
	public Player getDealer() {
		return players.get(0);
	}
	public void playDealersHand() {
		while (players.get(0).getSingleHand(0).getScore() < 17) {
			getDealer().hit(deck, 0);
		}
	}
	public Player getUser() {
		return players.get(1);
	}
	public double getUserBet() {
		return getUser().getBet();
	}
	public double getUserNumberOfChips() {
		return getUser().getNumberOfChips();
	}
	public ArrayList<Hand> getUserHands(){
		return getUser().getHands();
	}
	public boolean doesUserHaveAPair() {
		int cardOne = getSingleUserHand(0).getCardsInHand().get(0).value;
		int cardTwo = getSingleUserHand(0).getCardsInHand().get(1).value;
		if(cardOne == cardTwo) {
			return true;
		} else {
			return false;
		}
	}
	public ArrayList<Hand> getDealerHands(){
		return getDealer().getHands();
	}
	public Hand getSingleUserHand(int handToGet) {
		return getUser().getSingleHand(handToGet);
	}
	public int getDealerScore(){
		return getDealerHands().get(0).getScore();
	}
	public int getUserHandScore(Hand handToGetScoreOf){
		return handToGetScoreOf.getScore();
	}
	public boolean areUserHandsOver() {
		return handsOver;
	}
	public boolean isHandWon(int handToCheck) {
		return handsWon.get(handToCheck);
	}
	public boolean isHandPushed(int handToCheck) {
		return handsPushed.get(handToCheck);
	}
}
