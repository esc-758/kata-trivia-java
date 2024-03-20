package trivia;

import trivia.player.PlayerManager;
import trivia.question.QuestionManager;

// REFACTOR ME
public class GameBetter implements IGame {

   private final PlayerManager playerManager = new PlayerManager();
   private final QuestionManager questionManager = new QuestionManager();

   public boolean add(String playerName) {
      playerManager.addNewPlayer(playerName);
      return true;
   }

   public void roll(int roll) {
      System.out.println(playerManager.playerName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (!playerManager.isPlayerInPenaltyBox()) {
         movePlayerForwardAndAskQuestion(roll);
         return;
      }

       if (GamesRules.canPlayerGetOutOfPenaltyBox(roll)) {
          playerManager.takePlayerOutOfPenaltyBox();
          movePlayerForwardAndAskQuestion(roll);
       } else {
           System.out.println(playerManager.playerName() + " is not getting out of the penalty box");
       }
   }

   private void movePlayerForwardAndAskQuestion(int roll) {
      playerManager.movePlayerForward(roll);
      questionManager.askQuestion(playerManager.playerPlace());
   }

   public boolean wasCorrectlyAnswered() {
      if (playerManager.isPlayerInPenaltyBox()) {
         playerManager.goToNextPlayerTurn();
         return true;
      }

      return playTurn();
   }

   private boolean playTurn() {
      rewardCorrectAnswer();
      boolean winner = GamesRules.didPlayerWin(playerManager.playerCoins());
      playerManager.goToNextPlayerTurn();

      return winner;
   }

   private void rewardCorrectAnswer() {
      System.out.println("Answer was correct!!!!");
      playerManager.rewardPlayer();
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      playerManager.penalisePlayer();
      playerManager.goToNextPlayerTurn();

      return true;
   }
}
