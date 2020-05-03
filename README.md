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
  
 ## Implemented stories (Iteration 3):<br/>
  A player should be given a startup screen after launching the game that describes how to play blackjack and allows them to set the game parameters<br>
  A player should be able to select the number of computer players to play with.<br/>
  A player should be able to set the game parameters themselves, or use default values.<br/>
  A player should be presented with a setup screen before playing that gives instructions on how to play the game.<br/>
  Computer players should be able to play their hands in order according to randomly selected hit/stand rules.<br/>
 
 ## Stories to implement for Iteration 3:<br/>
  A player should be able to have the option to have card counting tools displayed on the screen while playing.<br/>
  A player should be able to surrender.<br/>
  A player should be able to take insurance.<br/>

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
