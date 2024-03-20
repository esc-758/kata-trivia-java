package trivia;

import static trivia.GameBetter.TOTAL_PLACES_ON_BOARD;

public final class Player {
    private final String name;
    private int place;
    private int coins;
    private boolean inPenaltyBox;

    public Player(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public int place() {
        return place;
    }

    public int coins() {
        return coins;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void moveForward(int noOfPlaces) {
        place += noOfPlaces;

        if (place >= TOTAL_PLACES_ON_BOARD) {
            place -= TOTAL_PLACES_ON_BOARD;
        }
    }

    public void addCoin() {
        coins++;
    }

    public void goToPenaltyBox() {
        inPenaltyBox = true;
    }

    public void leavePenaltyBox() {
        inPenaltyBox = false;
    }
}
