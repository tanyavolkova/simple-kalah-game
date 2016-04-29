package tanyavolkova.examples.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tetiana Volkova
 */
public class Board {

    private List<PitSet> pitSets;

    public Board() {
        pitSets = new ArrayList<>(2);
        pitSets.add(new PitSet(0));
        pitSets.add(new PitSet(1));
    }

    public PitSet getPitSet(int id) {
        int pitSetId = (id < 0) ? 0 : (id > 1) ? 1 : id;
        return pitSets.get(pitSetId);
    }

    public PitSet getOpponentPitSet(int id) {
        int pitSetId = (id < 0) ? 0 : (id > 1) ? 1 : id;
        int opponentPitSetId = Math.abs(pitSetId - 1);
        return pitSets.get(opponentPitSetId);
    }
}
