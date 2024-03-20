package trivia;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

// REFACTOR ME
public class GameBetter implements IGame {
   public static final int TOTAL_PLACES_ON_BOARD = 12;
   List<Player> players = new ArrayList<>();
   int[] places = new int[6];
   int[] purses = new int[6];
   boolean[] inPenaltyBox = new boolean[6];

   Deque<String> popQuestions = new LinkedList<>();
   Deque<String> scienceQuestions = new LinkedList<>();
   Deque<String> sportsQuestions = new LinkedList<>();
   Deque<String> rockQuestions = new LinkedList<>();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public GameBetter() {
      initialiseQuestionDecks();
   }

   private void initialiseQuestionDecks() {
      for (int i = 0; i < 50; i++) {
         popQuestions.addLast("Pop Question " + i);
         scienceQuestions.addLast(("Science Question " + i));
         sportsQuestions.addLast(("Sports Question " + i));
         rockQuestions.addLast("Rock Question " + i);
      }
   }

   public boolean add(String playerName) {
      initialisePlayer(playerName);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   private void initialisePlayer(String playerName) {
      places[noOfPlayers()] = 0;
      purses[noOfPlayers()] = 0;
      inPenaltyBox[noOfPlayers()] = false;
      players.add(new Player(playerName));
   }

   public int noOfPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      String currentPlayerName = players.get(this.currentPlayer).name();
      System.out.println(currentPlayerName + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (isInPenaltyBox()) {
         if (canGetOutOfPenaltyBox(roll)) {
            leavePenaltyBox(currentPlayerName);
            moveForwardAndAskQuestion(roll);
         } else {
            stayInPenaltyBox(currentPlayerName);
         }
      } else {
         moveForwardAndAskQuestion(roll);
      }

   }

   private boolean isInPenaltyBox() {
      return inPenaltyBox[currentPlayer];
   }

   private static boolean canGetOutOfPenaltyBox(int roll) {
      return roll % 2 != 0;
   }

   private void leavePenaltyBox(String currentPlayerName) {
      isGettingOutOfPenaltyBox = true;

      System.out.println(currentPlayerName + " is getting out of the penalty box");
   }

   private void moveForwardAndAskQuestion(int roll) {
      moveForward(roll);
      askQuestion();
   }

   private void moveForward(int roll) {
      places[currentPlayer] += roll;

      if (places[currentPlayer] >= TOTAL_PLACES_ON_BOARD) {
         places[currentPlayer] = places[currentPlayer] - TOTAL_PLACES_ON_BOARD;
      }

      System.out.printf("%s's new location is %s%n", players.get(currentPlayer).name(), places[currentPlayer]);
   }

   private void askQuestion() {
      System.out.println("The category is " + currentCategory());

      switch (currentCategory()) {
      case POP -> System.out.println(popQuestions.removeFirst());
      case SCIENCE -> System.out.println(scienceQuestions.removeFirst());
      case SPORTS -> System.out.println(sportsQuestions.removeFirst());
      case ROCK -> System.out.println(rockQuestions.removeFirst());
      }
   }

   private void stayInPenaltyBox(String currentPlayerName) {
      System.out.println(currentPlayerName + " is not getting out of the penalty box");
      isGettingOutOfPenaltyBox = false;
   }

   private Category currentCategory() {
      return switch (places[currentPlayer]) {
         case 0, 4, 8 -> Category.POP;
         case 1, 5, 9 -> Category.SCIENCE;
         case 2, 6, 10 -> Category.SPORTS;
         default -> Category.ROCK;
      };
   }

   public boolean wasCorrectlyAnswered() {
      if (isInPenaltyBox()) {
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
      purses[currentPlayer]++;
      System.out.printf("%s now has %d Gold Coins.%n", players.get(currentPlayer).name(), purses[currentPlayer]);
   }

   private void goToNextPlayerTurn() {
      currentPlayer++;
      if (currentPlayer == players.size()) {
         currentPlayer = 0;
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer).name() + " was sent to the penalty box");

      putPlayerInPenaltyBox();
      goToNextPlayerTurn();

      return true;
   }

   private void putPlayerInPenaltyBox() {
      inPenaltyBox[currentPlayer] = true;
   }

   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == 6);
   }
}
