package com.levi9.tvolkova.domain;

import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * @author Tetiana Volkova
 */
@Component
public class Rules {

    private int nextTurnPitSetId = 0;
    private int winnerPitSetId = -1;

    public int getNextTurnId() {
        return nextTurnPitSetId;
    }

    public int getWinnerPitSetId() {
        return winnerPitSetId;
    }

    public void apply(Board board, int pitSetId, int pitId) {
        if (!isLegalTurn(board, pitSetId, pitId)) {
            // how to inform user about illegal turn?
            return;
        }
        moveStones(board, pitSetId, pitId);
        defineGameResult(board, pitSetId);
    }

    private boolean isLegalTurn(Board board, int pitSetId, int pitId) {
        PitSet pitSet = board.getPitSet(pitSetId);
        Pit pit = pitSet.getPit(pitId);

        return pit.isNotEmpty() && pit.isNotKalah();
    }

    private void moveStones(Board board, int pitSetId, int pitId) {
        PitSet pitSet = board.getPitSet(pitSetId);
        PitSet opponentPitSet = board.getOpponentPitSet(pitSetId);

        nextTurnPitSetId = opponentPitSet.getId();

        Pit pit = pitSet.getPit(pitId);
        int stones = pit.getStones();
        pit.empty();

        int pitIdStartMoveTo = pitId + 1;

        while (stones > 0) {
            stones = moveStonesToOwnPits(pitSet, pitIdStartMoveTo, stones, opponentPitSet);

            if (stones > 0) {
                stones = moveStonesToOpponentPits(opponentPitSet, stones);
            }
            pitIdStartMoveTo = 0;
        }
    }

    private void defineGameResult(Board board, int pitSetId) {
        PitSet pitSet = board.getPitSet(pitSetId);
        PitSet opponentPitSet = board.getOpponentPitSet(pitSetId);

        if (!canMakeNextTurn(pitSet)) {
            nextTurnPitSetId = -1;
            finish(pitSet, opponentPitSet);
        }
    }

    private void finish(PitSet pitSet, PitSet opponentPitSet) {
        moveStonesToKalah(pitSet);
        moveStonesToKalah(opponentPitSet);

        int stones = pitSet.getKalah().getStones();
        int opponentStones = opponentPitSet.getKalah().getStones();

        if (stones == opponentStones) {
            winnerPitSetId = -1;
            return;
        }

        winnerPitSetId = stones > opponentStones ? pitSet.getId() : opponentPitSet.getId();
    }

    private boolean canMakeNextTurn(PitSet pitSet) {
        return !pitSet.isEmpty();
    }

    private void moveStonesToKalah(PitSet pitSet) {
        Pit kalah = pitSet.getKalah();
        for (Pit pit : pitSet.getPits()) {
            if (pit.isKalah()) {
                continue;
            }
            kalah.addStones(pit.getStones());
            pit.empty();
        }
    }

    private int moveStonesToOwnPits(PitSet pitSet, int startIndex, int count, PitSet opponentPitSet) {
        int stones = count;
        Iterator<Pit> iterator = pitSet.getPits().listIterator(startIndex);
        Pit pit;

        while (stones > 1 && iterator.hasNext()) {
            pit = iterator.next();
            pit.addStone();
            stones--;
        }
        // the last stone to the own pit
        if (stones == 1 && iterator.hasNext()) {
            pit = iterator.next();

            if (pit.isKalah()) {
                nextTurnPitSetId = pitSet.getId();
            }
            if (pit.isEmpty() && pit.isNotKalah()) {
                Pit kalah = pitSet.getKalah();
                kalah.addStone();

                int oppositeIndex = Math.abs(pit.getId() - pitSet.getPits().size() + 2);
                Pit opponentPit = opponentPitSet.getPit(oppositeIndex);
                kalah.addStones(opponentPit.getStones());
                opponentPit.empty();
            } else {
                pit.addStone();
            }
            stones--;
        }
        return stones;
    }

    private int moveStonesToOpponentPits(PitSet pitSet, int count) {
        Iterator<Pit> iterator = pitSet.getPits().iterator();
        int stones = count;
        Pit pit;

        while (stones > 0 && iterator.hasNext()) {
            pit = iterator.next();
            if (pit.isKalah()) {
                return stones;
            }
            pit.addStone();
            stones--;
        }

        if (stones == 0) {
            nextTurnPitSetId = Math.abs(pitSet.getId());
        }
        return stones;
    }
}
