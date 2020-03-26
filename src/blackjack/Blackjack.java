package blackjack;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Blackjack implements ActionListener{

	private JFrame frame;
	private int frameHeight = 700;
	private int frameWidth = 700;
	private Deck deck = new Deck();
	private Player dealer = new Player();
	private Player user = new Player();

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

	/**
	 * Initialize the contents of the frame.
	 */
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
	}
	
	private void createInputButton(int xPosition, int yPosition, String commandToPerform) {
		JButton newButton = new JButton(commandToPerform);
		newButton.setBounds(xPosition, yPosition, 100, 30);
		newButton.addActionListener(this);
		newButton.setActionCommand(commandToPerform);
		frame.getContentPane().add(newButton);
	}
	
	private void getGameParameters() {
		String s = (String)JOptionPane.showInputDialog(frame, "How many decks to play with (up to 8)?", "Blackjack", JOptionPane.QUESTION_MESSAGE);
		try {
			while(true) {
				if(s.length() == 1 && deck.setNumberOfDecks(s.charAt(0) - '0')) {
					break;
				} else {
					JOptionPane.showMessageDialog(frame, "Please enter a number 1-8", "Error", JOptionPane.ERROR_MESSAGE);
					s = (String)JOptionPane.showInputDialog(frame, "How many decks to play with (up to 8)?");
				}
			}
		} catch (NullPointerException e) { //Cancel button or exit button pressed, so exit the program
			System.exit(0);
		}
	}
	
	private void runHand() {
		deck.shuffle();
		deck.dealOutHands();
		displayHandsOnFrame();
	}
	
	private void displayHandsOnFrame() {
		addHandToFrame(user.hand, 0,0);
		addHandToFrame(dealer.hand, 0,0);
		//cover first of dealer's cards with card back image
	}
	
	private void addHandToFrame(Card[] handToDisplay, int xPosition, int yPosition) {
		
	}
	
	private void clearHandsFromFrame() {
		frame.getContentPane().removeAll();
		initializeInput();
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		System.out.println(action);
		switch(action) {
			case "Hit":
				user.hit();
				if(user.score > 21) {
					//display loss text
					clearHandsFromFrame();
					runHand();
				}
				break;
			case "Stand":
				user.stand();
					//display other dealer's card
					//display win/loss text
				clearHandsFromFrame();
				runHand();
				break;
		}
		
	}

}
