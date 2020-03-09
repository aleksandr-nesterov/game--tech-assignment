package com.bol.tech.assignment.core;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;

@ToString
@EqualsAndHashCode
public class Player {

    private final String id;
    private final int[] pits;

    private int largePit;
    private boolean turn;

    public Player(String id) {
        this.id = id;
        this.pits = new int[] {6, 6, 6, 6, 6, 6};
    }

    public Player(String id, int[] pits) {
        this.id = id;
        this.pits = pits;
    }

    private Player(String id, int[] pits, int largePit, boolean turn) {
        this.id = id;
        this.pits = pits;
        this.largePit = largePit;
        this.turn = turn;
    }

    public int captureStones(int pit) {
        int stones = pits[pit];
        pits[pit] = 0;
        return stones;
    }

    public void addStoneToSmallPit(int pit) {
        if (pit >= pits.length) {
            throw new IllegalArgumentException("Pit index is " + pit);
        }

        if (isSmallPit(pit)) {
            pits[pit]++;
        }
    }

    public void addStoneToLargePit() {
        largePit++;
    }

    public boolean isSmallPit(int pit) {
        return pit < pits.length;
    }

    public boolean isEmptyPit(int pit) {
        return pits[pit] == 0;
    }

    public boolean isMyTurn() {
        return turn;
    }

    public boolean allPitsAreEmpty() {
        return Arrays.stream(pits).allMatch(pit -> pit == 0);
    }

    public boolean hasAnotherTurn(int stones) {
        return turn = stones == 0;
    }

    public void addStonesToLargePit(int stones) {
        largePit += stones;
    }

    public void myTurn() {
        this.turn = true;
    }

    public int[] getPits() {
        return this.pits;
    }

    public int getLargePit() {
        return this.largePit;
    }

    public int getAllStones() {
        return Arrays.stream(pits).sum() + largePit;
    }

    public String getId() {
        return this.id;
    }

    public Player copy() {
        return new Player(id, Arrays.copyOf(pits, pits.length), largePit, turn);
    }

    public void clearTurn() {
        this.turn = false;
    }

    public boolean hasId(String playerId) {
        return this.getId().equals(playerId);
    }
}
