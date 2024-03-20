package trivia;

public class GamesRules {
    public static final int TOTAL_PLACES_ON_BOARD = 12;

    private GamesRules() {}

    public static boolean canPlayerGetOutOfPenaltyBox(int roll) {
        return roll % 2 != 0;
    }

    public static boolean didPlayerWin(int coins) {
        return coins != 6;
    }
}