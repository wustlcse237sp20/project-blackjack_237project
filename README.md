# cse237-project

## Implemented stories (Iteration 1):<br/>
  A player should be able to choose how many decks to play with.<br/>
  A player should be able to hit and stand.<br/>
  A player should be able to see the scores and hands of the dealer and player throughout a round at the appropriate times.<br/>
  A player should be notified if they won, lost, or pushed on a hand.<br/>
  A player should be able to choose to start a new hand.<br/>
  A dealer should be able to shuffle the deck.<br/>
  A dealer should be able to deal out cards.<br/>

## Implemented stories (Iteration 2):<br/>
  A player should be able to choose to start the game with a certain number of chips. <br/>
  A player should be able to bet on each hand and recieve winnings based on payout rules.<br/>
  A player should be able to split their hand.<br/>
  A dealer should be able to complete their hand based on hitting rules for the dealer.<br/>
  A player should be able to double down on their hand.<br/>
  An ace should be worth 1 or 11 depending on which gives the hand the best score.<br/>
  
 ## Stories to implement for Iteration 3:<br/>
  A player should be able to select the number of computer players to play with.<br/>
  Computer players should be able to play their hands in order according to randomly selected hit/stand rules.<br/>
  A player should be able to have the option to have card counting tools displayed on the screen while playing.<br/>
  A player should be able to surrender.<br/>
  A player should be able to take insurance.<br/>
  
## Features that don't work:<br/>
  The double down button should only appear before deciding to hit<br/>
  The double down button should only appear if the user has enough chips to double their bet<br/>
  The split button should only appear if the starting hand contains a pair<br/>
  An extra "Hand Lost" notification appears when the dealer busts<br/>
  

## Instructions to launch the game:<br/>

### MacOS or Linux:<br/>
Open a terminal window and navigate to the root directory of the project<br/>
run the command: ./MacOSBlackjack.sh<br/>

### Windows<br/>
Because the game requires a GUI (it is not headless), on windows it must be launched from the windows command prompt not linux subsystem for windows:<br/>

Open a windows command prompt (windows key + r, type in cmd, click ok)<br/>
Navigate to the root directory of the repository using: cd <path\to\file><br/>
run the command: WinBlackjack.bat<br/>
(Alternatively, just double click the .bat file in the project directory from the file browser)<br/>

## Playing a game:<br/>
Follow the prompts to set up the parameters for the game (including entering the deck size, and starting number of chips). Then play a hand using the hit and stand buttons that appear at the bottom of the screen displaying the delt cards (it is possible that these buttons may have previously been appearing off the edge of the frame. This seems to have been fixed, but if you can't see any buttons, try resizing the window to be larger by dragging on the lower edge).
