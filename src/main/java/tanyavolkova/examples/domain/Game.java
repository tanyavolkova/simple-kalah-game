package tanyavolkova.examples.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tetiana Volkova
 */
public class Game {

    private List<Player> players;

    private Board board;

    private Player nextTurnPlayer;
    private Player winner;

    private Rules rules;

    // todo: remove game by expiring time
    private LocalDateTime creationTime;

    public Game(Player player, Player opponent, Rules rules) {
        this.rules = rules;

        initPlayers(player, opponent);
        nextTurnPlayer = player;

        board = new Board();
        creationTime = LocalDateTime.now();
    }

    public void makeTurn(Player player, int pitId) {
        rules.apply(board, player.getPitsetId(), pitId);

        defineNextTurnPlayer(rules.getNextTurnId());
        defineWinner(rules.getWinnerPitSetId());
    }

    private void defineNextTurnPlayer(int nextTurnPitSetId) {
        nextTurnPlayer = findPlayerByPitSetId(nextTurnPitSetId);
    }

    private void defineWinner(int winnerPitSetId) {
        winner = findPlayerByPitSetId(winnerPitSetId);
    }

    public Player getNextTurnPlayer() {
        return nextTurnPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean gameOver() {
        return winner != null || nextTurnPlayer == null;
    }

    public Player findPlayer(String id) {
        for (Player player : players) {
            if (player.getId().equals(id)) {
                return player;
            }
        }
        return null;
    }

    public Player getOpponent(String id) {
        int playerIndex = getPlayerIndex(id);
        if (playerIndex == -1) {
            return null;
        }
        int opponentIndex = Math.abs(playerIndex - 1);
        return players.get(opponentIndex);
    }

    // TODO how to pass game state to ui?
    Board getBoard() {
        return board;
    }

    private void initPlayers(Player player, Player opponent) {
        players = new ArrayList<>(2);
        players.add(player);
        players.add(opponent);
    }

    private int getPlayerIndex(String id) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private Player findPlayerByPitSetId(int id) {
        for (Player player : players) {
            if (player.getPitsetId() == id) {
                return player;
            }
        }
        return null;
    }
}
