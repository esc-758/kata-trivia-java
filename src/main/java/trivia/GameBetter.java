package trivia;

import java.util.ArrayList;
import java.util.List;

import trivia.players.Player;

// REFACTOR ME
public class GameBetter implements IGame {
   public static final int TOTAL_PLACES_ON_BOARD = 12;

   private final QuestionManager questionManager = new QuestionManager();
   List<Player> players = new ArrayList<>();
   Player currentPlayer;

   public boolean add(String playerName) {
      addNewPlayer(playerName);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());

      if (players.size() == 1) {
         currentPlayer = players.get(0);
      }

      return true;
   }

   private void addNewPlayer(String playerName) {
      players.add(new Player(playerName));
   }

   public void roll(int roll) {
      System.out.println(currentPlayer.name() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (!currentPlayer.isInPenaltyBox()) {
         movePlayerForwardAndAskQuestion(roll);
         return;
      }

       if (playerCanGetOutOfPenaltyBox(roll)) {
           takePlayerOutOfPenaltyBox();
           movePlayerForwardAndAskQuestion(roll);
       } else {
           System.out.println(currentPlayer.name() + " is not getting out of the penalty box");
       }
   }

   private static boolean playerCanGetOutOfPenaltyBox(int roll) {
      return roll % 2 != 0;
   }

   private void takePlayerOutOfPenaltyBox() {
      currentPlayer.leavePenaltyBox();
      System.out.println(currentPlayer.name() + " is getting out of the penalty box");
   }

   private void movePlayerForwardAndAskQuestion(int roll) {
      movePlayerForward(roll);
      questionManager.askQuestion(currentPlayer.place());
   }

   private void movePlayerForward(int roll) {
      currentPlayer.moveForward(roll);

      System.out.printf("%s's new location is %s%n", currentPlayer.name(), currentPlayer.place());
   }

   public boolean wasCorrectlyAnswered() {
      if (currentPlayer.isInPenaltyBox()) {
         goToNextPlayerTurn();
         return true;
      }

      return playTurn();
   }

   private boolean playTurn() {
      rewardCorrectAnswer();
      boolean winner = didPlayerWin();
      goToNextPlayerTurn();

      return winner;
   }

   private void rewardCorrectAnswer() {
      System.out.println("Answer was correct!!!!");
      currentPlayer.addCoin();
      System.out.printf("%s now has %d Gold Coins.%n", currentPlayer.name(), currentPlayer.coins());
   }

   private void goToNextPlayerTurn() {
      if (players.indexOf(currentPlayer) == players.size() - 1) {
         currentPlayer = players.get(0);
      } else {
         currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(currentPlayer.name() + " was sent to the penalty box");

      currentPlayer.goToPenaltyBox();
      goToNextPlayerTurn();

      return true;
   }

   private boolean didPlayerWin() {
      return currentPlayer.coins() != 6;
   }
}
