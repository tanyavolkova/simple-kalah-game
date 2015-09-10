package com.levi9.tvolkova.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tetiana Volkova
 */
@Component
public class GameManager {

    @Autowired
    private Rules rules;

    private Map<String, Game> games = new HashMap<>();

    public Game registerPlayers(Player one, Player two) {
        String id = one.getId() + "_" + two.getId();
        Game g = games.get(id);
        if (g == null) {
            Game game = new Game(one, two, rules);
            games.put(id, game);
            return game;
        }
        return g;
    }

    public Game turn(Player player, int pitId) {
        String gameId = findGameId(player.getId());
        if (gameId == null) {
            // output error here
            return null;
        }
        Game game = games.get(gameId);
        game.makeTurn(player, pitId);

        return game;
    }

    public Game findGame(String playerId) {
        String gameId = findGameId(playerId);
        return games.get(gameId);
    }

    private String findGameId(String playerId) {
        String[] playerIds;

        for (String gameId : games.keySet()) {
            playerIds = gameId.split("_");
            for (String id : playerIds) {
                if (id.equals(playerId)) {
                    return gameId;
                }
            }
        }
        return null;
    }
}
