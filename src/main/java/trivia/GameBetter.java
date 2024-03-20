package trivia;

import java.util.ArrayList;
import java.util.List;

// REFACTOR ME
public class GameBetter implements IGame {
   public static final int TOTAL_PLACES_ON_BOARD = 12;

   List<Player> players = new ArrayList<>();

   QuestionDeck<PopQuestion> popQuestions = new QuestionDeck<>();
   QuestionDeck<ScienceQuestion> scienceQuestions = new QuestionDeck<>();
   QuestionDeck<SportsQuestion> sportsQuestions = new QuestionDeck<>();
   QuestionDeck<RockQuestion> rockQuestions = new QuestionDeck<>();

   Player currentPlayer;
   boolean isGettingOutOfPenaltyBox;

   public GameBetter() {
      initialiseQuestionDecks();
   }

   private void initialiseQuestionDecks() {
      for (int i = 0; i < 50; i++) {
         popQuestions.add(new PopQuestion("Pop Question " + i));
         scienceQuestions.add(new ScienceQuestion("Science Question " + i));
         sportsQuestions.add(new SportsQuestion("Sports Question " + i));
         rockQuestions.add(new RockQuestion("Rock Question " + i));
      }
   }

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

      if (currentPlayer.isInPenaltyBox()) {
         if (canGetOutOfPenaltyBox(roll)) {
            leavePenaltyBox(currentPlayer.name());
            moveForwardAndAskQuestion(roll);
         } else {
            stayInPenaltyBox(currentPlayer.name());
         }
      } else {
         moveForwardAndAskQuestion(roll);
      }

   }

   private static boolean canGetOutOfPenaltyBox(int roll) {
      return roll % 2 != 0;
   }

   private void leavePenaltyBox(String currentPlayerName) {
      isGettingOutOfPenaltyBox = true;

      System.out.println(currentPlayerName + " is getting out of the penalty box");
   }

   private void moveForwardAndAskQuestion(int roll) {
      movePlayerForward(roll);
      askQuestion();
   }

   private void movePlayerForward(int roll) {
      currentPlayer.moveForward(roll);

      System.out.printf("%s's new location is %s%n", currentPlayer.name(), currentPlayer.place());
   }

   private void askQuestion() {
      System.out.println("The category is " + currentCategory());

      switch (currentCategory()) {
         case POP -> System.out.println(popQuestions.draw().getText());
         case SCIENCE -> System.out.println(scienceQuestions.draw().getText());
         case SPORTS -> System.out.println(sportsQuestions.draw().getText());
         case ROCK -> System.out.println(rockQuestions.draw().getText());
      }
   }

   private void stayInPenaltyBox(String currentPlayerName) {
      System.out.println(currentPlayerName + " is not getting out of the penalty box");
      isGettingOutOfPenaltyBox = false;
   }

   private Category currentCategory() {
      return switch (currentPlayer.place()) {
         case 0, 4, 8 -> Category.POP;
         case 1, 5, 9 -> Category.SCIENCE;
         case 2, 6, 10 -> Category.SPORTS;
         default -> Category.ROCK;
      };
   }

   public boolean wasCorrectlyAnswered() {
      if (currentPlayer.isInPenaltyBox()) {
         if (isGettingOutOfPenaltyBox) {
            return playTurn();
         } else {
            goToNextPlayerTurn();
            return true;
         }
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
