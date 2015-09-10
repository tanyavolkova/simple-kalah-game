package com.levi9.tvolkova.domain;

import java.util.List;

/**
 * @author Tetiana Volkova
 */
public class BoardView {

    private Game game;
    private Player player;
    private Player nextTurnPlayer;
    private Player winner;

    private String opponentName;
    private int opponentKalah;
    private int[] opponentsPits;

    private List<Pit> pits;
    private int kalah;
    
    private boolean yourTurn;
    private boolean gameOver;
    private String resultMessage;

    public BoardView(Game game, String playerId) {
        this(game, game.findPlayer(playerId));
    }

    public BoardView(Game game, Player player) {
        this.game = game;
        this.player = player;

        nextTurnPlayer = game.getNextTurnPlayer();
        winner = game.getWinner();

        yourTurn = player.equals(nextTurnPlayer);
        gameOver = game.gameOver();
        defineResultMessage(game);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public int getOpponentKalah() {
        return opponentKalah;
    }

    public int[] getOpponentsPits() {
        return opponentsPits;
    }

    public String getPlayerName() {
        return player.getName();
    }

    public List<Pit> getPits() {
        return pits;
    }

    public int getKalah() {
        return kalah;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public BoardView build() {
        Board board = game.getBoard();
        retrieveOpponentsName();
        retrieveOpponentsData(board);
        retrieveOwnData(board);
        return this;
    }

    private void retrieveOpponentsName() {
        Player opponent = game.getOpponent(player.getId());
        opponentName = opponent.getName();
    }

    private void retrieveOpponentsData(Board board) {
        PitSet opponentPitSet = board.getOpponentPitSet(player.getPitsetId());
        List<Pit> pits = opponentPitSet.getPits();
        opponentsPits = new int[6];

        for (Pit pit : pits) {
            if (pit.isKalah()) {
                opponentKalah = pit.getStones();
            } else {
                opponentsPits[pit.getId()] = pit.getStones();
            }
        }
    }

    private void retrieveOwnData(Board board) {
        PitSet pitSet = board.getPitSet(player.getPitsetId()).getClone();
        pits = pitSet.getPits();
        Pit kalahPit = pitSet.getKalah();
        pits.remove(kalahPit);
        kalah = kalahPit.getStones();
    }

    private void defineResultMessage(Game game) {
        if (!gameOver) {
            return;
        }
        if (winner == null) {
            resultMessage = "No winner";
        } else {
            resultMessage = "Winner is " + winner.getName();
        }
    }
}
