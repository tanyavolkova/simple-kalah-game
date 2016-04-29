package tanyavolkova.examples.controllers;

import tanyavolkova.examples.domain.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tetiana Volkova
 */
@Component
public class WaitingPlayers {

    private List<Player> players = new ArrayList<>();

    public boolean add(Player player) {
        if (players.contains(player)) {
            return false;
        }
        players.add(player);
        return true;
    }

    public boolean remove(String playerId) {
        return remove(get(playerId));
    }

    public boolean remove(Player player) {
        if (players.contains(player)) {
            players.remove(player);
            return true;
        }
        return false;
    }

    public Player pop() {
        if (players.isEmpty()) {
            return null;
        }
        Player player = players.get(0);
        players.remove(0);
        return player;
    }

    public Player get(String playerId) {
        int index = players.indexOf(new Player("", playerId, 1));
        if (index == -1) {
            return null;
        }
        return players.get(index);
    }

    public boolean contains(String playerId) {
        return players.contains(new Player("", playerId, 1));
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }
}
