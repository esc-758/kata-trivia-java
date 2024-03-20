package trivia;

import java.util.ArrayList;
import java.util.List;

public class PenaltyBox {
    private final List<Player> players = new ArrayList<>();

    public void addPlayer(Player player) {
        players.add(player);
    }

    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
}
