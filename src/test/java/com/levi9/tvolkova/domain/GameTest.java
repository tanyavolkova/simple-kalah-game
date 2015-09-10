package com.levi9.tvolkova.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Tetiana Volkova
 */
public class GameTest {

    private Player player;
    private Player opponent;
    private Game game;

    @Before
    public void setUp() {
        player = new Player("one", "1", 1);
        opponent = new Player("two", "2", 2);
        game = new Game(player, opponent, new Rules());
    }

    @Test
    public void testFirstTurn() {
        int firstPit = 0;
        game.makeTurn(player, firstPit);

        Board board = game.getBoard();
        PitSet opponentPitSet = board.getOpponentPitSet(player.getPitsetId());
        List<Pit> opponentPits = opponentPitSet.getPits();
        for (Pit pit : opponentPits) {
            if (pit.isKalah()) {
                assertEquals(0, pit.getStones());
            } else {
                assertEquals(6, pit.getStones());
            }
        }

        PitSet pitSet = board.getPitSet(player.getPitsetId());
        List<Pit> pits = pitSet.getPits();
        assertEquals(0, pits.get(0).getStones());
        assertEquals(7, pits.get(1).getStones());
        assertEquals(7, pits.get(2).getStones());
        assertEquals(7, pits.get(3).getStones());
        assertEquals(7, pits.get(4).getStones());
        assertEquals(7, pits.get(5).getStones());
        assertEquals(1, pits.get(6).getStones());
    }

    @Test
    public void testFourTurns() {
        game.makeTurn(player, 0);
        game.makeTurn(player, 1);
        game.makeTurn(player, 2);
        game.makeTurn(player, 5);

        Board board = game.getBoard();
        PitSet opponentPitSet = board.getOpponentPitSet(player.getPitsetId());
        List<Pit> opponentPits = opponentPitSet.getPits();
        assertEquals(9, opponentPits.get(0).getStones());
        assertEquals(9, opponentPits.get(1).getStones());
        assertEquals(8, opponentPits.get(2).getStones());
        assertEquals(8, opponentPits.get(3).getStones());
        assertEquals(0, opponentPits.get(4).getStones());
        assertEquals(7, opponentPits.get(5).getStones());
        assertEquals(0, opponentPits.get(6).getStones());

        PitSet pitSet = board.getPitSet(player.getPitsetId());
        List<Pit> pits = pitSet.getPits();
        assertEquals(1, pits.get(0).getStones());
        assertEquals(0, pits.get(1).getStones());
        assertEquals(0, pits.get(2).getStones());
        assertEquals(9, pits.get(3).getStones());
        assertEquals(9, pits.get(4).getStones());
        assertEquals(0, pits.get(5).getStones());
        assertEquals(12, pits.get(6).getStones());
    }
}