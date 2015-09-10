package com.levi9.tvolkova.domain;

/**
 * @author Tetiana Volkova
 */
public class Pit {

    private int id;
    private int stones;
    private boolean isKalah;

    public Pit(int id, int stones, boolean isKalah) {
        this.id = id;
        this.stones = stones;
        this.isKalah = isKalah;
    }

    public int getId() {
        return id;
    }

    public int getStones() {
        return stones;
    }

    public boolean isKalah() {
        return isKalah;
    }

    public boolean isNotKalah() {
        return !isKalah();
    }

    public boolean isEmpty() {
        return stones == 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public void empty() {
        stones = 0;
    }

    public void addStone() {
        addStones(1);
    }

    public void addStones(int amount) {
        stones += amount;
    }

    public void removeStone() {
        stones += 1;
    }

    public Pit getClone() {
        return  new Pit(this.id, this.stones, this.isKalah);
    }
}
