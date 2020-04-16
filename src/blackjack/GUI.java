package blackjack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class GUI implements ActionListener{
	
	private Blackjack gameInstance;
	private JFrame frame;
	private int frameHeight;
	private int frameWidth;
	private  DecimalFormat df = new DecimalFormat("#.00");
	
	public GUI(int frameHeight, int frameWidth, Blackjack gameInstance) {
		this.frameHeight = frameHeight;
		this.frameWidth = frameWidth;
		this.gameInstance = gameInstance;
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			System.out.println("Failed to use nimbus look and feel");
		}
		frame = new JFrame();
		frame.setResizable(true);
		frame.getContentPane().setBackground(new Color(0, 128, 0));
		frame.getContentPane().setLayout(null);
		frame.setBounds(0, 0, frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void showUserInterface() {
		frame.setVisible(true);
	}
	
	public int askForNumberOfDecksToUse() {
		try {
			String numberOfDecksToUse = showNumberOfDecksPrompt();
			while(!numberOfDecksToUse.matches("[0-9]+")) {
				showDeckSizeError();
				numberOfDecksToUse = showNumberOfDecksPrompt();
			}
			return numberOfDecksToUse.charAt(0) - '0';
		} catch (NullPointerException e){
			System.exit(0);
		}
		return 1;
	}
	private String showNumberOfDecksPrompt() {
		return (String)JOptionPane.showInputDialog(
							frame, 
							"How many decks to play with (up to 8)?", 
							"Blackjack", 
							JOptionPane.QUESTION_MESSAGE);
	}
	public void showDeckSizeError() {
		JOptionPane.showMessageDialog(frame, "Please enter a number 1-8", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public int askForNumberOfChips() {
		try {
			String numberOfChips = showNumberOfChipsPrompt();
			while(!numberOfChips.matches("[0-9]+")) {
				showChipNumberError();
				numberOfChips = showNumberOfChipsPrompt();
			}
			return Integer.parseInt(numberOfChips);
		} catch (NullPointerException e){
			System.exit(0);
		}
		return 1;
	}
	private String showNumberOfChipsPrompt() {
		return (String) JOptionPane.showInputDialog(
										frame, 
										"How many chips to start with (Whole dollars only, $100-$999,999)?", 
										"Blackjack", 
										JOptionPane.QUESTION_MESSAGE);
	}
	public void showChipNumberError() {
		JOptionPane.showMessageDialog(frame, "Please enter a whole number between 100 and 999999", "Error", JOptionPane.ERROR_MESSAGE);
	}
	public void showChipsGoneMessage() {
		JOptionPane.showMessageDialog(frame, "You have run out of chips", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public int askForBet() {
		try {
			String betAmount = showBetPrompt();
			while(!betAmount.matches("[0-9]+")) {
				showBetAmountError();
				betAmount = showBetPrompt();
			}
			return Integer.parseInt(betAmount);
		} catch (NullPointerException e){
			System.exit(0);
		}
		return 1;
	}
	private String showBetPrompt() {
		return (String)JOptionPane.showInputDialog(
							frame, 
							"How much to bet (Whole dollars only, $1 - $" + String.valueOf(df.format(gameInstance.getUser().getNumberOfChips())) + ")?", 
							"Blackjack", 
							JOptionPane.QUESTION_MESSAGE);
	}
	public void showBetAmountError() {
		JOptionPane.showMessageDialog(frame, 
				"Please enter a whole number between 1 and " + String.valueOf(df.format(gameInstance.getUser().getNumberOfChips())), 
				"Error", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Adds all the buttons and text to the GUI
	 */
	public void initializeGUI() {
		createInputButton(frameWidth/2 - 110, frameHeight - 100, "Hit");
		createInputButton(frameWidth/2, frameHeight - 100,"Stand");
		createInputButton(frameWidth/2 - 220, frameHeight - 100, "Split");
		createInputButton(frameWidth/2 + 110, frameHeight - 100, "Double Down");
		createTextLabel(150, 40, "Dealer");
		createTextLabel(frameHeight - 290, 60, "Score: " + String.valueOf(gameInstance.getUserScore()));
		createTextLabel(frameHeight - 305, 300, "Bet: $" + String.valueOf(df.format(gameInstance.getUser().getBet())));
		if(gameInstance.getUser().getNumberOfChips() > 0) {
			createTextLabel(frameHeight - 335, 300, "Chips: $" + String.valueOf(df.format(gameInstance.getUser().getNumberOfChips())));
		} else {
			createTextLabel(frameHeight - 335, 85, "Chips: $0.00");
		}
	}
	
	public void createInputButton(int xPosition, int yPosition, String commandToPerform) {
		JButton newButton = new JButton(commandToPerform);
		newButton.setBounds(xPosition, yPosition, 110, 30);
		newButton.addActionListener(this);
		newButton.setActionCommand(commandToPerform);
		frame.getContentPane().add(newButton);
	}
	
	public void createTextLabel(int yPosition, int width, String labelText) {
		JLabel labelToCreate = new JLabel(labelText, SwingConstants.CENTER);
		labelToCreate.setBounds(frameWidth/2 - width/2, yPosition, width, 30);
		labelToCreate.setForeground(new Color(255,255,255));
		frame.getContentPane().add(labelToCreate);
	}
	
	/**
	 * Adds images to the GUI for the cards in each player's hand, optionallt hiding one of the dealer's cards
	 * @param coverDealerCard
	 */
	public void displayHandsOnFrame(boolean coverDealerCard) {
		clearHandsFromFrame();
		addPlayerHandToFrame(gameInstance.getUser().getHand(), frameWidth/2-40, frameHeight-250);
		if(coverDealerCard) {
			addDealerHandToFrame(gameInstance.getDealer().getHand(), frameWidth/2-40, 15);
		} else {
			addPlayerHandToFrame(gameInstance.getDealer().getHand(), frameWidth/2-40, 15);
		}
	}
	
	/**
	 * Removes the images for all player's hands from the GUI
	 */
	public void clearHandsFromFrame() {
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
	public void addPlayerHandToFrame(ArrayList<Card> handToDisplay, int xPositionStart, int yPosition) {
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
	public void addDealerHandToFrame(ArrayList<Card> handToDisplay, int xPositionStart, int yPosition) {
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
	public void addCardToFrame(String imageFileToBeDisplayed, int xPosition, int yPosition) {
		JLabel cardToDisplay = new JLabel();
	    cardToDisplay.setIcon(new ImageIcon(this.getClass().getResource(imageFileToBeDisplayed)));
	    cardToDisplay.setBounds(xPosition, yPosition, 94, 216);
		frame.getContentPane().add(cardToDisplay);
	}
	

	@Override
	/**
	 * Handles the press of any buttons on the GUI and the corresponding game actions
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch(action) {
			case "Hit":
				gameInstance.handleHitPress();
				break;
			case "Stand":
				gameInstance.handleStandPress();
				break;
		}
	}
	
	public boolean askToPlayNewHand() {
		int reply = JOptionPane.showConfirmDialog(null, "Play another hand?", "Blackjack", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION){
			return true;
        } else {
        	return false;
        }
	}
}
