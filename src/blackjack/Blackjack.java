package blackjack;

import java.awt.EventQueue;
import java.util.ArrayList;

public class Blackjack {
	private GUI userInterface;
	private int frameHeight = 800;
	private int frameWidth = 800;
	private boolean displayGUI = true;
	
	private Deck deck;
	private ArrayList<Player> players = new ArrayList<Player>(); //first player in list is the dealer, second is the user, all rest are computer controlled
	private boolean handsOver = false;
	public ArrayList<Boolean> handsWon;
	private ArrayList<Boolean> handsPushed;
	private boolean wonInsurance = false;

	/**
	 * Launches the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Blackjack window = new Blackjack();
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
		setupGameParameters();
	}
	protected void setupGameParameters() {
		StartupGUI setupGUI = new StartupGUI(this);
		setupGUI.showSetupWindow();
	}
	
	/**
	 * Deals out a hand to the players and displays the appropriate cards on the GUI
	 */
	public void playAHand() {
		if(displayGUI) {
			userInterface.showUserInterface();
		}
		handsOver = false;
		handsWon.clear();
		handsPushed.clear();
		if(getUserNumberOfChips() < 1) {
			userInterface.showChipsGoneMessage();
			resetNumberOfChips();
		}
		setBetAmount();
		deck.dealOutHands(players);
		for(int i = 1; i < 3; i++) {
			if(getNumberOfComputerPlayers() >= i) {
				players.get(i+1).hitBasedOnRules(players.get(0), deck, (int) Math.random()*6+1);
				userInterface.displayHandsOnFrame(true);
			}
		}
		userInterface.displayHandsOnFrame(true);
	}
	/**
	 * Prompts the user to ask for the number of chips to 
	 */
	private void resetNumberOfChips() {
		int numberOfChipsToStartWith = userInterface.askForNumberOfChips();
		while(numberOfChipsToStartWith < 100 || numberOfChipsToStartWith > 999999) {
			userInterface.showChipNumberError();
			numberOfChipsToStartWith = userInterface.askForNumberOfChips();
		}
		getUser().setChipAmount(numberOfChipsToStartWith);
	}
	/**
	 * Prompts the user to ask for how much to bet on a hand
	 */
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
				runComputerPlayersAfterUser();
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
					runComputerPlayersAfterUser();
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
				userInterface.incrementControllingHandNumber();
			}
			playDealersHand();
			runComputerPlayersAfterUser();
			determineWinnerOfHand();
			userInterface.displayHandsOnFrame(false);
			finishHand();
			if(startNewHand()) {
				playAHand();
			}
		}
	}
	public void runComputerPlayersAfterUser() {
		for(int i = 3; i < 5; i++) {
			if(getNumberOfComputerPlayers() >= i) {
				players.get(i+1).hitBasedOnRules(players.get(0), deck, (int) Math.random()*6+1);
				userInterface.displayHandsOnFrame(true);

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
		getUser().subtractChips((int)getUser().getBet());
		getUser().setBet(2*(int)getUser().getBet());
		getUser().hit(deck,  0);
		handleStandPress();
	}
	public void handleSurrenderPress() {
		//TODO: Implement surrendering
		getUser().addChips((int)(0.5*getUser().getBet()));
		//this stuff handles finishing out the rest of the round after the user surrenders, it shouldn't need to be touched
		playDealersHand();
		runComputerPlayersAfterUser();
		handsWon.add(false);
		handsPushed.add(false);
		handsOver = true;
		userInterface.displayHandsOnFrame(false);
		finishHand();
		if(startNewHand()) {
			playAHand();
		}
		
	}
	public void handleInsurancePress() {
		//TODO: Implement taking insurance, this will require adding some code to determineWinnerOfHand to see if the insurance bet 
		//needs to be paid out at the end of a hand
		int insuranceChips = (int)(0.5*getUser().getBet());
		if (getUser().getNumberOfChips() >= insuranceChips) {
			getUser().subtractChips(insuranceChips);
			wonInsurance = true;
		}
		else {
			return;
		}
		userInterface.displayHandsOnFrame(true);
	}
	
	/**
	 * Plays out the remainder of the dealer's hand after all players have finished theirs
	 */
	public void playDealersHand() {
		if(getDealerHand().getScore()==17) {
			ArrayList<Card> hand = getDealerHand().getCardsInHand();
			for (Card card : hand) {
				if(card.getValue()==11) {
					getDealer().hit(deck,  0);
					return;
				}
			}
		}
		while (getDealerHand().getScore() < 17) {
			getDealer().hit(deck, 0);
		}
	}
	
	/**
	 * Determines based on the player's and dealers score/hand if the player won the hand and pays out bets accordingly
	 */
	private void determineWinnerOfHand() {
		for(Hand handToCheck : getUser().getHands()) {
			int insuranceChips = (int)(0.5*getUser().getBet());
			if(getDealerHands().get(0).doesHandHaveBlackjack() && wonInsurance) {
				getUser().addChips((int)(getUser().getBet())+insuranceChips);
				handsWon.add(false);
				handsPushed.add(false);
			}
			else if(handToCheck.getScore() <= 21) { //if user doesnt bust on hand
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
	
	/**
	 * Determines if a new hand should be played based on a user prompt (or direct input via method override in JUnit tests)
	 * @return true if a new hand should be played
	 */
	protected boolean startNewHand() {
		return userInterface.askToPlayNewHand();
	}
	
	
	//-----------------------Setters and getters-----------------------//
	
	public void setStartingChipNumber(int numberOfChips) {
		getUser().setChipAmount(numberOfChips);
	}
	public void setDeck(int numberOfDecksToUse) {
		deck = new Deck(numberOfDecksToUse);
		deck.shuffle();
	}
	public void setNumberOfComputerPlayers(int numberOfPlayers) {
		for(int i = 0; i < numberOfPlayers + 2; i++) {
			players.add(new Player());
		}
	}
	public void setDisplayCardCounter(boolean displayCardCounter) {
		userInterface.setDisplayCardCounter(displayCardCounter);
	}
	public void setDisplayGUI(boolean displayGUI) {
		this.displayGUI = displayGUI;
	}
	public GUI getUserInterface() {
		return this.userInterface;
	}
	public int getDeckCount() {
		return deck.getCount();
	}
	public ArrayList<Hand> getComputerPlayerHands(int numberToGet) {
		return players.get(numberToGet+1).getHands();
	}
	public int getNumberOfComputerPlayers() {
		return players.size()-2;
	}
	public Player getDealer() {
		return players.get(0);
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
		return getSingleUserHand(0).doesHandContainPair();
	}
	public ArrayList<Hand> getDealerHands(){
		return getDealer().getHands();
	}
	public Hand getDealerHand() {
		return getDealer().getSingleHand(0);
	}
	public int getVisibleDealerCard() {
		return getDealerHand().getCardsInHand().get(0).getValue();
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
	public int getComputerPlayerScore(int playerToGetScoreOf) {
		return players.get(playerToGetScoreOf + 1).getSingleHand(0).getScore();
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
	public void setInsurance(boolean wonInsurance) {
		this.wonInsurance = wonInsurance;
	}
}
