package blackjack;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class StartupGUI implements ActionListener{
	private JFrame startFrame;
	private JTextField deckSizeInput;
	private JTextField chipNumberInput;
	private JTextField computerPlayerNumberInput;
	private Blackjack gameInstance;
	private JCheckBox cardCounterVisible;
	private int frameWidth = 1000;
	private int frameHeight = 900;
	
	public StartupGUI(Blackjack gameInstance) {
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
	}
	/**
	 * Displays a window with information on how to play blackjack and allows user to specify game parameters
	 */
	public void showSetupWindow() {
		startFrame = new JFrame();
		startFrame.setResizable(true);
		startFrame.getContentPane().setBackground(new Color(0, 128, 0));
		startFrame.getContentPane().setLayout(null);
		startFrame.setBounds(0, 0, frameWidth, frameHeight);
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		addTutorialText();
		addDeckInputField();
		addChipInputField();
		addComputerPlayerInputField();
		addCardCounterInput();
		
		JButton startButton = new JButton("Start Game");
		startButton.setBounds(frameWidth/2-55, frameHeight - 90, 110, 30);
		startButton.addActionListener(this);
		startButton.setActionCommand("Start Game");
		startFrame.getContentPane().add(startButton);
		
		startFrame.setVisible(true);
	}
	private void addTutorialText() {
		JLabel tutorialText = new JLabel("<html><h1>How to play Blackjack:</h1>"
				+ "The goal of Blackjack is to end a round with cards that score as close to 21 as possible without going over 21."
				
				+ "<h2>Scoring cards in a hand</h2>"
				+ "2-10 are worth their face value<br>"
				+ "Jacks, Queens, and Kings are worth 10<br>"
				+ "Aces are worth either 1 or 11, whichever gives the better score"
				
				+ "<h2>Actions during a round</h2>"
				+ "During a round, buttons for the following actions will appear on screen under the player's hand. If a given action cannot be performed on the current hand, then the corresponding button will not appear.<br>"
				+ "<b>Hit:</b> Ask the dealer to deal an additional card to your hand.<br>"
				+ "<b>Stand:</b> End the round with your current hand, allowing the dealer to play out their hand.<br>"
				+ "<b>Double Down:</b> Double your bet on your hand and ask the dealer for one additional card before ending the round. This can only be performed if it is your first action of the round.<br>"
				+ "<b>Split:</b> Seperate the two cards in your hand into two seperate hands, which can then be played using hits/stand as normal. This can only be performed if your hand contains a pair and if it is your first action of the round.<br>"
				+ "<b>Take Insurance:</b> If the dealer is displaying an ace, you may bet an additional amount equal to half of your original bet. If the dealer shows blackjack, then you win chips equal to your insurance bet even if you lose your original bet. This can only be performed as your first action in a round.<br>"
				+ "<b>Surrender:</b> Fold your hand to recieve back half of your bet. This can only be performed as your first action in a round"
				
				+ "<h2>Bet Payout</h2>"
				+ "If a player gets a better score than the dealer without exceeding 21 they win chips equal to their bet. <br>"
				+ "If delt blackjack (two cards that score exactly 21) then the player wins 1.5 times their bet. <br>"
				+ "If the player and dealer end a hand with the same score, the player recieves their bet back, but no additional winnings.<br>"
				+ "If the dealer has blackjack but a player took insurance, the player will lose their original bet and win back chips equal to the insurance bet (with a net result of losing half of their original bet instead of the full amount).<br>"
				+ "If a player surrenders, they recieve half of their bet back."
		
				+ "<h2>Begining a round of Blackjack</h2>"
				+ "Set the parameters for the game below (or leave them at their default values) and press start game. A prompt will appear "
				+ "asking how much you would like to bet for the first round, then the round will begin and your hand will be displayed in "
				+ "a new window along with the buttons to perform actions for that round.</html>");
		tutorialText.setBounds(50, 20, frameWidth - 100, 645);
		tutorialText.setFont(new Font("SansSerif", Font.PLAIN, 14));
		startFrame.getContentPane().add(tutorialText);
	}
	/**
	 * Adds an input field for the user to set the number of decks to use
	 */
	private void addDeckInputField() {
		JLabel deckInputLabel = new JLabel("How many decks of 52 cards to use? Enter a number 1-8:");
		deckInputLabel.setBounds(frameWidth/2 - 300, frameHeight-220, 475, 30);
		deckInputLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		startFrame.getContentPane().add(deckInputLabel);
		deckSizeInput = new JTextField("1", 1);
		deckSizeInput.setBounds(frameWidth/2 + 225, frameHeight-220, 50, 30);
		startFrame.getContentPane().add(deckSizeInput);
	}
	/**
	 * Adds an input field for the user to set the number of chips to start with
	 */
	private void addChipInputField() {
		JLabel chipInputLabel = new JLabel("How many chips to start with? Enter a whole number 100 - 1000000:");
		chipInputLabel.setBounds(frameWidth/2 - 300, frameHeight-190, 475, 30);
		chipInputLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		startFrame.getContentPane().add(chipInputLabel);
		chipNumberInput = new JTextField("1000", 1);
		chipNumberInput.setBounds(frameWidth/2 + 225, frameHeight-190, 50, 30);
		startFrame.getContentPane().add(chipNumberInput);
	}
	/**
	 * Adds an input field for the user to set the number of computer players at the table
	 */
	private void addComputerPlayerInputField() {
		JLabel computerPlayerInputLabel = new JLabel("How many computer players to play with? Enter a number 1 - 4:");
		computerPlayerInputLabel.setBounds(frameWidth/2 - 300, frameHeight-160, 475, 30);
		computerPlayerInputLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		startFrame.getContentPane().add(computerPlayerInputLabel);
		computerPlayerNumberInput = new JTextField("0", 1);
		computerPlayerNumberInput.setBounds(frameWidth/2 + 225, frameHeight-160, 50, 30);
		startFrame.getContentPane().add(computerPlayerNumberInput);
	}
	
	private void addCardCounterInput() {
		JLabel cardCounterInputLabel = new JLabel("Display the count of the deck throughout rounds?");
		cardCounterInputLabel.setBounds(frameWidth/2 - 300, frameHeight - 130, 475, 30);
		cardCounterInputLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cardCounterVisible = new JCheckBox();
		cardCounterVisible.setBounds(frameWidth/2 + 225, frameHeight - 130, 30, 30);
		startFrame.getContentPane().add(cardCounterVisible);
		startFrame.getContentPane().add(cardCounterInputLabel);
	}
	/**
	 * Checks to ensure all user provided input is valid
	 * @return
	 */
	private boolean checkInput() {
		if(checkDeckInput() && checkChipInput() && checkPlayerInput()) {
			return true;
		} else {
			return false;
		}
	}
	private boolean checkDeckInput() {
		if(!deckSizeInput.getText().matches("[0-9]+")) {
			showDeckSizeError();
			return false;
		} else {
			int deckSizeToSet = Integer.parseInt(deckSizeInput.getText());
			if(deckSizeToSet < 1 || deckSizeToSet > 8) {
				showDeckSizeError();
				return false;
			} else {
			return true;
			}
		}
	}
	private boolean checkChipInput() {
		if(!chipNumberInput.getText().matches("[0-9]+")) {
			showChipNumberError();
			return false;
		} else {
			int chipNumberToSet = Integer.parseInt(chipNumberInput.getText());
			if(chipNumberToSet < 100 || chipNumberToSet > 1000000) {
				showChipNumberError();
				return false;
			} else {
				return true;
			}
		}
	}
	private boolean checkPlayerInput() {
		if(!computerPlayerNumberInput.getText().matches("[0-9]+")) {
			showPlayerNumberError();
			return false;
		} else {
			int playerNumberToSet = Integer.parseInt(computerPlayerNumberInput.getText());
			if(playerNumberToSet < 0 || playerNumberToSet > 4) {
				showPlayerNumberError();
				return false;
			} else {
				return true;
			}
		}
	}
	private void showDeckSizeError() {
		JOptionPane.showMessageDialog(startFrame, "Please enter a number 1-8 for the deck size", "Error", JOptionPane.ERROR_MESSAGE);
	}
	private void showChipNumberError() {
		JOptionPane.showMessageDialog(startFrame, "Please enter a whole number 100-100000 for the chip number", "Error", JOptionPane.ERROR_MESSAGE);
	}
	private void showPlayerNumberError() {
		JOptionPane.showMessageDialog(startFrame, "Please enter a number 0-4 for the number of computer players", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch(action) {
			case "Start Game":
				if(checkInput()) {
					gameInstance.setNumberOfComputerPlayers(Integer.parseInt(computerPlayerNumberInput.getText()));
					gameInstance.setDeck(Integer.parseInt(deckSizeInput.getText()));
					gameInstance.setStartingChipNumber(Integer.parseInt(chipNumberInput.getText()));
					gameInstance.setDisplayCardCounter(cardCounterVisible.isSelected());
					startFrame.setVisible(false);
					gameInstance.playAHand();
				}
				break;
		}
		
	}

}

