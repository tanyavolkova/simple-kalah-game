package com.levi9.tvolkova.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Tetiana Volkova
 */
public class PitSet {

    private int id;

    private List<Pit> pits = new ArrayList<>(7);

    public PitSet(int id) {
        this.id = id;
        
        init();
    }

    private void init() {
        for (int i = 0; i < 6; i++) {
            pits.add(new Pit(i, 6, false));
        }
        pits.add(new Pit(7, 0, true));
    }

    public int getId() {
        return id;
    }

    public List<Pit> getPits() {
        return pits;
    }

    public Pit getPit(int id) {
        return pits.get(id);
    }

    public Pit getKalah() {
        return pits.get(6);
    }

    public boolean isEmpty() {
        for (Pit pit : pits) {
            if (!pit.isKalah() && pit.isNotEmpty()) {
                return false;
            }
        }
        return true;
    }

    public PitSet getClone() {
        PitSet pitSet = new PitSet(this.id);
        pitSet.pits = new ArrayList<>(this.pits.size());
        for (Pit pit : this.pits) {
            pitSet.pits.add(pit.getClone());
        }

        return pitSet;
    }
}
