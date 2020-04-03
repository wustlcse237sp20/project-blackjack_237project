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
	private Deck deck = new Deck();
	public Player dealer = new Player();
	public Player user = new Player();
	private ArrayList<Player> players = new ArrayList<Player>();
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
		players.add(user);
		players.add(dealer);
		initializeFrame();
		initializeInput();
		getGameParameters();
		runHand();
	}
	private void runHand() {
		
		deck.shuffle();
//		for(int i =0;i<1;i++) {
//			for(Player player: players) {
//				player.hand.add(deck.allCardsInGame.get(0));
//				System.out.println("before remove");
//				deck.allCardsInGame.remove(0);
//			}
//		}
//		user.hand.add(new Card("S13"));
//		user.hand.add(new Card("S13"));
//		dealer.hand.add(new Card("S13"));
//		dealer.hand.add(new Card("S13"));
		user.hit(deck.allCardsInGame.get(0));
		deck.allCardsInGame.remove(0);
		user.hit(deck.allCardsInGame.get(0));
		deck.allCardsInGame.remove(0);
		dealer.hit(deck.allCardsInGame.get(0));
		deck.allCardsInGame.remove(0);
		dealer.hit(deck.allCardsInGame.get(0));
		deck.allCardsInGame.remove(0);
		System.out.println(user.hand);
		System.out.println(dealer.hand);
		displayHandsOnFrame(true);
		System.out.println("display");
	}
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		System.out.println(action);
		switch(action) {
			case "Hit":
				user.hit(deck.allCardsInGame.get(0));
				deck.allCardsInGame.remove(0);
				displayHandsOnFrame(true);
				if(user.score > 21) {
					//display loss text
					runEndOfHandCleanup();
				}
				break;
			case "Stand":
				user.stand();
				displayHandsOnFrame(false);
				//display win/loss text
				runEndOfHandCleanup();
				break;
		}
	}
	private void runEndOfHandCleanup() {
		int reply = JOptionPane.showConfirmDialog(null, "Play another hand?", "Blackjack", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION){
			runHand();
        } else {
        	System.exit(0);
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
	
}
