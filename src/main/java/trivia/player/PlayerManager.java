package trivia.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private final List<Player> players = new ArrayList<>();
    private Player currentPlayer;

    public void addNewPlayer(String playerName) {
        players.add(new Player(playerName));

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());

        if (players.size() == 1) {
            currentPlayer = players.get(0);
        }
    }

    public void goToNextPlayerTurn() {
        if (players.indexOf(currentPlayer) == players.size() - 1) {
            currentPlayer = players.get(0);
        } else {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        }
    }

    public void rewardPlayer() {
        currentPlayer.addCoin();
        System.out.printf("%s now has %d Gold Coins.%n", currentPlayer.name(), currentPlayer.coins());
    }

    public void penalisePlayer() {
        currentPlayer.goToPenaltyBox();

        System.out.println(currentPlayer.name() + " was sent to the penalty box");
    }

    public boolean isPlayerInPenaltyBox() {
        return currentPlayer.isInPenaltyBox();
    }

    public void takePlayerOutOfPenaltyBox() {
        System.out.println(currentPlayer.name() + " is getting out of the penalty box");

        currentPlayer.leavePenaltyBox();
    }

    public void movePlayerForward(int noOfPlaces) {
        currentPlayer.moveForward(noOfPlaces);

        System.out.printf("%s's new location is %s%n", currentPlayer.name(), currentPlayer.place());
    }

    public String playerName() {
        return currentPlayer.name();
    }

    public int playerPlace() {
        return currentPlayer.place();
    }

    public int playerCoins() {
        return currentPlayer.coins();
    }
}