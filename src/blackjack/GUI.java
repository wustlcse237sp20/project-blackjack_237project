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
	private DecimalFormat decimalFormat = new DecimalFormat("#.00");
	private int controllingHandNumber = 0;
	private boolean actionPressed = false;
	private boolean displayCardCounter;
	
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
	/**
	 * Adds all the buttons and text to the GUI
	 */
	public void initializeGUI() {
		if(!gameInstance.areUserHandsOver()) { //only allow interaction if playing a hand
			if(controllingHandNumber > 0) {
				createTextLabel(frameWidth/2 - 50, frameHeight - 100,  120,  "Controlling Hand: " + String.valueOf(controllingHandNumber));
			}
			createInputButton(frameWidth/2 - 125, frameHeight - 130, "Hit");
			createInputButton(frameWidth/2, frameHeight - 130,"Stand");
			if(!actionPressed && controllingHandNumber == 0) {
				createInputButton(frameWidth/2 - 250, frameHeight - 130, "Surrender");
			}
			if(gameInstance.getUserNumberOfChips() >= gameInstance.getUserBet() && !actionPressed && controllingHandNumber == 0) {
				createInputButton(frameWidth/2 + 125, frameHeight - 130, "Double Down");
			}
			if(gameInstance.getUserNumberOfChips() >= gameInstance.getUserBet()/2 && !actionPressed && controllingHandNumber == 0 && gameInstance.getVisibleDealerCard() == 11) {
				createInputButton(frameWidth/2 - 125, frameHeight - 100, "Take Insurance");
			}
			if(gameInstance.getUserNumberOfChips() >= gameInstance.getUserBet() && !actionPressed && controllingHandNumber == 0 && gameInstance.doesUserHaveAPair()) {
				createInputButton(frameWidth/2, frameHeight - 100, "Split");
			}
		}
		createTextLabel(frameWidth/2 - 20, 150, 40, "Dealer");
		
		if(gameInstance.getUserHands().size() ==  1) {
			createTextLabel(frameWidth/2 - 30, frameHeight - 320, 60, "Score: " + String.valueOf(gameInstance.getUserHandScore(gameInstance.getSingleUserHand(0))));
			createTextLabel(frameWidth/2 - 150, frameHeight - 335, 300, "Bet: $" + String.valueOf(decimalFormat.format(gameInstance.getUserBet())));
		} else {
			for(int i = 0; i < gameInstance.getUserHands().size(); i++) {
				createTextLabel(frameWidth/2 - 125 + i*175, frameHeight - 305, 60, "Score: " + String.valueOf(gameInstance.getUserHandScore(gameInstance.getSingleUserHand(i))));
				createTextLabel(frameWidth/2 - 245 + i*175, frameHeight - 320, 300, "Bet: $" + String.valueOf(decimalFormat.format(gameInstance.getUserBet())));
				createTextLabel(frameWidth/2 - 125 + i*175, frameHeight - 335, 60, "Hand: " + String.valueOf(i+1));
			}
		}
		if(gameInstance.getUserNumberOfChips() > 0) {
			createTextLabel(frameWidth/2 - 150, frameHeight - 400, 300, "Chips: $" + String.valueOf(decimalFormat.format(gameInstance.getUserNumberOfChips())));
		} else {
			createTextLabel(frameWidth/2 - 42, frameHeight - 400, 85, "Chips: $0.00");
		}
		if(displayCardCounter) {
			createTextLabel(frameWidth/2 - 50, frameHeight/2-50, 100, "Count: " + String.valueOf(gameInstance.getDeckCount()));
		}
	}
	public void createInputButton(int xPosition, int yPosition, String commandToPerform) {
		JButton newButton = new JButton(commandToPerform);
		newButton.setBounds(xPosition, yPosition, 125, 30);
		newButton.addActionListener(this);
		newButton.setActionCommand(commandToPerform);
		frame.getContentPane().add(newButton);
	}
	public void createTextLabel(int xPosition, int yPosition, int width, String labelText) {
		JLabel labelToCreate = new JLabel(labelText, SwingConstants.CENTER);
		labelToCreate.setBounds(xPosition, yPosition, width, 30);
		labelToCreate.setForeground(new Color(255,255,255));
		frame.getContentPane().add(labelToCreate);
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
							"How much to bet (Whole dollars only, $1 - $" + String.valueOf(decimalFormat.format(gameInstance.getUser().getNumberOfChips())) + ")?", 
							"Blackjack", 
							JOptionPane.QUESTION_MESSAGE);
	}
	public void showBetAmountError() {
		JOptionPane.showMessageDialog(frame, 
				"Please enter a whole number between 1 and " + String.valueOf(decimalFormat.format(gameInstance.getUser().getNumberOfChips())), 
				"Error", 
				JOptionPane.ERROR_MESSAGE);
	}	
	/**
	 * Adds images to the GUI for the cards in each player's hand, optionallt hiding one of the dealer's cards
	 * @param coverDealerCard
	 */
	public void displayHandsOnFrame(boolean coverDealerCard) {
		clearHandsFromFrame();
		for(int i = 0; i < gameInstance.getNumberOfComputerPlayers(); i++) {
			if(i < 2) {
				createTextLabel(frameWidth - 230, 135+i*200, 150, "Computer Player " + (i+1));
				createTextLabel(frameWidth - 230, 150+i*200, 150, "Score: " + String.valueOf(gameInstance.getComputerPlayerScore(i+1)));
				addPlayerHandToFrame(gameInstance.getComputerPlayerHands(i+1), frameWidth-200, 175+i*200);
			} else {
				createTextLabel(95, frameHeight - 465 - (i-2)*200, 150, "Computer Player " + (i+1));
				createTextLabel(95, frameHeight - 450 - (i-2)*200, 150, "Score: " + String.valueOf(gameInstance.getComputerPlayerScore(i+1)));
				addPlayerHandToFrame(gameInstance.getComputerPlayerHands(i+1), 125, frameHeight - 425 - (i-2)*200);
			}
		}
		addPlayerHandToFrame(gameInstance.getUserHands(), frameWidth/2-40, frameHeight-280);
		if(coverDealerCard) {
			addDealerHandToFrame(gameInstance.getDealerHands(), frameWidth/2-40, 15);
		} else {
			addPlayerHandToFrame(gameInstance.getDealerHands(), frameWidth/2-40, 15);
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
	public void addPlayerHandToFrame(ArrayList<Hand> handsToDisplay, int xPositionStart, int yPosition) {
		if(handsToDisplay.size() == 1) {
			ArrayList<Card> cardsToDisplay = handsToDisplay.get(0).getCardsInHand();
			for(int i = cardsToDisplay.size() - 1; i >= 0; i--) {
				addCardToFrame(cardsToDisplay.get(i).getCardImageFilePath(), xPositionStart-(cardsToDisplay.size()-i-1)*15, yPosition);
			}
		} else {
			int newXPositionStart = xPositionStart - 100;
			for(int i = 0; i < handsToDisplay.size(); i++) {
				ArrayList<Card> cardsToDisplay = handsToDisplay.get(i).getCardsInHand();
				for(int j = cardsToDisplay.size() - 1; j >= 0; j--) {
					addCardToFrame(cardsToDisplay.get(j).getCardImageFilePath(), newXPositionStart-(cardsToDisplay.size()-j-1)*15, yPosition);
				}
				newXPositionStart += 175;
			}
		}
	}
	/**
	 * Takes the ArrayList of cards representing the dealer's hand and displays it on the GUI at the supplied x and y
	 * positions, flipping one of the cards face down.
	 * @param handToDisplay the dealer's hand of cards
	 * @param xPositionStart the x poistion on the GUI to start displaying the cards
	 * @param yPosition the y position on the GUI to diplay the cards
	 */
	public void addDealerHandToFrame(ArrayList<Hand> handToDisplay, int xPositionStart, int yPosition) {
		ArrayList<Card> cardsToDisplay = handToDisplay.get(0).getCardsInHand();
		for(int i = cardsToDisplay.size() - 1; i >= 0; i--) {
			String imageFileName = cardsToDisplay.get(i).getCardImageFilePath();
			if(i == cardsToDisplay.size() - 1) {
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
		actionPressed = true;
		switch(action) {
			case "Hit":
				gameInstance.handleHitPress();
				break;
			case "Stand":
				gameInstance.handleStandPress();
				break;
			case "Split":
				controllingHandNumber = 1;
				gameInstance.handleSplitPress();
				break;
			case "Double Down":
				gameInstance.handleDoubleDownPress();
				break;
			case "Surrender":
				gameInstance.handleSurrenderPress();
				break;
			case "Take Insurance":
				gameInstance.handleInsurancePress();
				break;
		}
	}
	
	public boolean askToPlayNewHand() {
		actionPressed = false;
		gameInstance.setInsurance(false);
		int reply = JOptionPane.showConfirmDialog(null, "Play another hand?", "Blackjack", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION){
			return true;
        } else {
        	return false;
        }
	}
	public void incrementControllingHandNumber() {
		controllingHandNumber++;
		if(controllingHandNumber > 2) {
			controllingHandNumber = 0;
		}
	}
	
	//----------------------------Setters and Getters----------------------------//
	public int getControllingHandNumber(){
		return  controllingHandNumber;
	}
	public void setDisplayCardCounter(boolean displayCardCounter) {
		this.displayCardCounter = displayCardCounter;
	}

}
