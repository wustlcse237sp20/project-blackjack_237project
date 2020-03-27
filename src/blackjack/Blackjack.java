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
	public Deck deck = new Deck();
	public Player dealer = new Player();
	public Player user = new Player();
	public boolean handOver = false;
	public boolean handWon = false;

	/**
	 * Launch the application.
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
	 * Setup the window and run the game
	 */
	public Blackjack() {
		initializeFrame();
		initializeInput();
		getGameParameters();
		runHand();
	}
	private void runHand() {
		handOver = false;
		handWon = true;
		deck.shuffle();
		deck.dealOutHands();
		displayHandsOnFrame(true);
	}
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		System.out.println(action);
		switch(action) {
			case "Hit":
				user.hit();
				displayHandsOnFrame(true);
				if(user.score > 21) {
					handOver = true;
					handWon = false;
					if(askToPlayNewHand()) {
						runHand();
					}
				}
				break;
			case "Stand":
				user.stand();
				displayHandsOnFrame(false);
				//display win/loss text
				handOver = true;
				if(askToPlayNewHand()) {
					runHand();
				}
				break;
		}
	}
	private void initializeFrame() {		
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(0, 128, 0));
		frame.getContentPane().setLayout(null);
		frame.setBounds(0, 0, frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	private void initializeInput() {
		createInputButton(frameWidth/2 - 100, frameHeight - 50, "Hit");
		createInputButton(frameWidth/2, frameHeight - 50,"Stand");
		JLabel dealerLabel = new JLabel("Dealer");
		JLabel playerLabel = new JLabel("Your Hand");
		dealerLabel.setBounds(frameWidth/2-20, 150, 40, 30);
		dealerLabel.setForeground(new Color(255,255,255));
		playerLabel.setBounds(frameWidth/2-35, frameHeight - 225, 70, 30);
		playerLabel.setForeground(new Color(255,255,255));
		frame.getContentPane().add(dealerLabel);
		frame.getContentPane().add(playerLabel);
	}
	private void createInputButton(int xPosition, int yPosition, String commandToPerform) {
		JButton newButton = new JButton(commandToPerform);
		newButton.setBounds(xPosition, yPosition, 100, 30);
		newButton.addActionListener(this);
		newButton.setActionCommand(commandToPerform);
		frame.getContentPane().add(newButton);
	}
	private void getGameParameters() {
		int numberOfDecksToUse = askForNumberOfDecksToUse();
		boolean deckNumberSet = deck.setNumberOfDecks(numberOfDecksToUse);
		while(!deckNumberSet) {
			JOptionPane.showMessageDialog(frame, "Please enter a number 1-8", "Error", JOptionPane.ERROR_MESSAGE);
			deckNumberSet = deck.setNumberOfDecks(askForNumberOfDecksToUse());
		}
	}
	
	
	private void displayHandsOnFrame(boolean coverDealerCard) {
		clearHandsFromFrame();
		addPlayerHandToFrame(user.hand, frameWidth/2-40, frameHeight-200);
		if(coverDealerCard) {
			addDealerHandToFrame(dealer.hand, frameWidth/2-40, 15);
		} else {
			addPlayerHandToFrame(dealer.hand, frameWidth/2-40, 15);
		}
	}
	private void addPlayerHandToFrame(ArrayList<Card> handToDisplay, int xPositionStart, int yPosition) {
		for(int i = handToDisplay.size() - 1; i >= 0; i--) {
			addCardToFrame(handToDisplay.get(i).cardImageFilePath, xPositionStart-(handToDisplay.size()-i-1)*15, yPosition);
		}
	}
	private void addDealerHandToFrame(ArrayList<Card> handToDisplay, int xPositionStart, int yPosition) {
		for(int i = handToDisplay.size() - 1; i >= 0; i--) {
			String imageFileName = handToDisplay.get(i).cardImageFilePath;
			if(i == handToDisplay.size() - 1) {
				imageFileName = "/back.png";
			}
			addCardToFrame(imageFileName, xPositionStart-(handToDisplay.size()-i-1)*15, yPosition);
		}
	}
	private void addCardToFrame(String imageFileToBeDisplayed, int xPosition, int yPosition) {
		JLabel cardToDisplay = new JLabel();
	    cardToDisplay.setIcon(new ImageIcon(this.getClass().getResource(imageFileToBeDisplayed)));
	    cardToDisplay.setBounds(xPosition, yPosition, 94, 216);
		frame.getContentPane().add(cardToDisplay);
	}
	private void clearHandsFromFrame() {
		frame.getContentPane().removeAll();
		initializeInput();
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
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
	protected boolean askToPlayNewHand() {
		int reply = JOptionPane.showConfirmDialog(null, "Play another hand?", "Blackjack", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION){
			return true;
        } else {
        	return false;
        }
	}
}
