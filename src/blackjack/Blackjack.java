package blackjack;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Blackjack implements ActionListener{

	private JFrame frame;
	private int frameHeight = 700;
	private int frameWidth = 700;
	private Deck deck;
	private int numberOfPlayers = 2;
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
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Sets uo the application window and run the game
	 */
	public Blackjack() {
		setDeckSize();
		for(int i = 0; i < numberOfPlayers + 1; i++) {
			players.add(new Player());
		}
		initializeFrame();
		initializeGUI();
		playAHand();
	}
	
	private void setDeckSize() {
		int numberOfSingleDecksToUse = askForNumberOfDecksToUse();
		while(numberOfSingleDecksToUse < 1 || numberOfSingleDecksToUse > 8) {
			JOptionPane.showMessageDialog(frame, "Please enter a number 1-8", "Error", JOptionPane.ERROR_MESSAGE);
			numberOfSingleDecksToUse = askForNumberOfDecksToUse();
		}
		deck = new Deck(numberOfSingleDecksToUse);
	}
	protected int askForNumberOfDecksToUse() {
		try {
			String numberOfDecksToUse = (String)JOptionPane.showInputDialog(
													frame, 
													"How many decks to play with (up to 8)?", 
													"Blackjack", 
													JOptionPane.QUESTION_MESSAGE);
			while(numberOfDecksToUse.length() != 1) {
				JOptionPane.showMessageDialog(frame, "Please enter a number 1-8", "Error", JOptionPane.ERROR_MESSAGE);
				numberOfDecksToUse = (String)JOptionPane.showInputDialog(
													frame, 
													"How many decks to play with (up to 8)?", 
													"Blackjack", 
													JOptionPane.QUESTION_MESSAGE);
			}
			return numberOfDecksToUse.charAt(0) - '0';
		} catch (NullPointerException e){
			System.exit(0);
		}
		return 1;
	}
	/**
	 * Sets up the frame object for the GUI
	 */
	private void initializeFrame() {		
		frame = new JFrame();
		frame.setResizable(true);
		frame.getContentPane().setBackground(new Color(0, 128, 0));
		frame.getContentPane().setLayout(null);
		frame.setBounds(0, 0, frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	/**
	 * Adds all the buttons and text to the GUI
	 */
	private void initializeGUI() {
		createInputButton(frameWidth/2 - 110, frameHeight - 70, "Hit");
		createInputButton(frameWidth/2, frameHeight - 70,"Stand");
		createInputButton(frameWidth/2 - 220, frameHeight - 70, "Split");
		createInputButton(frameWidth/2 + 110, frameHeight - 70, "Double Down");
		createTextLabel(150, 40, "Dealer");
		createTextLabel(frameHeight - 245, 70, "Your Hand");
		createTextLabel(frameHeight - 260, 60, "Score: " + String.valueOf(getUser().getPlayerScore()));
		createTextLabel(frameHeight - 275, 60, "Bet: $25");
		createTextLabel(frameHeight - 305, 85, "Chips: $1000");
	}
	private void createInputButton(int xPosition, int yPosition, String commandToPerform) {
		JButton newButton = new JButton(commandToPerform);
		newButton.setBounds(xPosition, yPosition, 110, 30);
		newButton.addActionListener(this);
		newButton.setActionCommand(commandToPerform);
		frame.getContentPane().add(newButton);
	}
	private void createTextLabel(int yPosition, int width, String labelText) {
		JLabel labelToCreate = new JLabel(labelText);
		labelToCreate.setBounds(frameWidth/2 - width/2, yPosition, width, 30);
		labelToCreate.setForeground(new Color(255,255,255));
		frame.getContentPane().add(labelToCreate);
	}
	
	/**
	 * Deals out a hand to the players and displays the appropriate cards on the GUI
	 */
	private void playAHand() {
		handOver = false;
		handWon = false;
		deck.dealOutHands(players);
		displayHandsOnFrame(true);
	}
	/**
	 * Adds images to the GUI for the cards in each player's hand, optionallt hiding one of the dealer's cards
	 * @param coverDealerCard
	 */
	private void displayHandsOnFrame(boolean coverDealerCard) {
		clearHandsFromFrame();
		addPlayerHandToFrame(getUser().getHand(), frameWidth/2-40, frameHeight-220);
		if(coverDealerCard) {
			addDealerHandToFrame(getDealer().getHand(), frameWidth/2-40, 15);
		} else {
			addPlayerHandToFrame(getDealer().getHand(), frameWidth/2-40, 15);
		}
	}
	/**
	 * Removes the images for all player's hands from the GUI
	 */
	private void clearHandsFromFrame() {
		frame.getContentPane().removeAll();
		initializeGUI();
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
	}
	/**
	 * Takes the ArrayList of cards representing a player's hand and displays it face up on the GUI at the supplied x and y
	 * positions.
	 * @param handToDisplay the player's hand of cards
	 * @param xPositionStart the x poistion on the GUI to start displaying the cards
	 * @param yPosition the y position on the GUI to diplay the cards
	 */
	private void addPlayerHandToFrame(ArrayList<Card> handToDisplay, int xPositionStart, int yPosition) {
		for(int i = handToDisplay.size() - 1; i >= 0; i--) {
			addCardToFrame(handToDisplay.get(i).cardImageFilePath, xPositionStart-(handToDisplay.size()-i-1)*15, yPosition);
		}
	}
	/**
	 * Takes the ArrayList of cards representing the dealer's hand and displays it on the GUI at the supplied x and y
	 * positions, flipping one of the cards face down.
	 * @param handToDisplay the dealer's hand of cards
	 * @param xPositionStart the x poistion on the GUI to start displaying the cards
	 * @param yPosition the y position on the GUI to diplay the cards
	 */
	private void addDealerHandToFrame(ArrayList<Card> handToDisplay, int xPositionStart, int yPosition) {
		for(int i = handToDisplay.size() - 1; i >= 0; i--) {
			String imageFileName = handToDisplay.get(i).cardImageFilePath;
			if(i == handToDisplay.size() - 1) {
				imageFileName = "/back.png";
			}
			addCardToFrame(imageFileName, xPositionStart-(handToDisplay.size()-i-1)*15, yPosition);
		}
	}
	/**
	 * Adds the image file for a card at the supplied path to the GUI frame at the provided position
	 * @param imageFileToBeDisplayed the path to the image file to display (i.e. /13S.png, see Card class for convention)
	 * @param xPosition
	 * @param yPosition
	 */
	private void addCardToFrame(String imageFileToBeDisplayed, int xPosition, int yPosition) {
		JLabel cardToDisplay = new JLabel();
	    cardToDisplay.setIcon(new ImageIcon(this.getClass().getResource(imageFileToBeDisplayed)));
	    cardToDisplay.setBounds(xPosition, yPosition, 94, 216);
		frame.getContentPane().add(cardToDisplay);
	}
	
	/**
	 * Handles the press of any buttons on the GUI and the corresponding game actions
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch(action) {
			case "Hit":
				getUser().hit(deck);
				displayHandsOnFrame(true);
				if(getUser().getPlayerScore() > 21) {
					handWon = false;
					handOver = true;
					displayHandsOnFrame(false);
					displayHandOverText();
					if(askToPlayNewHand()) {
						playAHand();
					}
				}
				break;
			case "Stand":
				getDealer().playDealersHand();
				determineWinnerOfHand();
				handOver = true;
				displayHandsOnFrame(false);
				displayHandOverText();
				if(askToPlayNewHand()) {
					playAHand();
				}
				break;
		}
	}
	/**
	 * Determines based on the player's and dealers score/hand if the player won the hand
	 */
	private void determineWinnerOfHand() {
		if(getUser().getPlayerScore() > getDealer().getPlayerScore() || getDealer().getPlayerScore() > 21) { //if dealer busts or has lower score
			handWon = true;
		}
		if(getUser().getPlayerScore() < getDealer().getPlayerScore()) {
			handWon = false;
		}
		if(getUser().getPlayerScore() == getDealer().getPlayerScore()) { //if dealer and player have same score
			if(getDealer().doesPlayerHaveBlackjack() && !getUser().doesPlayerHaveBlackjack()) { //player loses if dealer has blackjack but player doesnt
				handWon = false;
			} else if(!getDealer().doesPlayerHaveBlackjack() && getUser().doesPlayerHaveBlackjack()){ //player wins if they have blackjack but dealer doesn't
				handWon = true;
			} else {
				handPushed = true;
			}
		}
	}
	/**
	 * Displays a notification in the center of the GUI norifying the player the outcome of the hand
	 */
	private void displayHandOverText() {
		createTextLabel(170, 60,"Score: " + String.valueOf(getDealer().getPlayerScore()));
		if(handWon) {
			//display win text
			createTextLabel(frameHeight/2-15, 65, "Hand Won");
		} else {
			if(handPushed) {
				//display draw text
				createTextLabel(frameHeight/2-15, 85, "Hand Pushed");
			} else {
				//diplay draw text
				createTextLabel(frameHeight/2-15, 65, "Hand Lost");
			}
		}
	}
	protected boolean askToPlayNewHand() {
		int reply = JOptionPane.showConfirmDialog(null, "Play another hand?", "Blackjack", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION){
			return true;
        } else {
        	return false;
        }
	}
	
	
	public Player getDealer(){
		return this.players.get(0);
	}
	public Player getUser(){
		return this.players.get(1);
	}
	public boolean isHandOver() {
		return this.handOver;
	}
	public boolean isHandWon() {
		return this.handWon;
	}
	public boolean isHandPush() {
		return this.handPushed;
	}}
