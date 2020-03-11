package com.bol.tech.assignment.core;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;

/**
 * This class is meant for internal use only. Represents the player of the game.
 * Its not thread-safe.
 */
@ToString
@EqualsAndHashCode
public class Player {

    private final String id;
    private final int[] pits;

    private int largePit;
    private boolean turn;

    Player(String id) {
        this(id, false);
    }

    Player(String id, boolean turn) {
        this(id, new int[] {6, 6, 6, 6, 6, 6}, 0, turn);
    }

    Player(String id, int[] pits) {
        this(id, pits, 0, false);
    }

    Player(String id, int[] pits, boolean turn) {
        this(id, pits, 0, turn);
    }

    Player(String id, int[] pits, int largePit, boolean turn) {
        this.id = id;
        this.pits = pits;
        this.largePit = largePit;
        this.turn = turn;
    }

    /**
     * Capture stones from the given pit.
     *
     * @param pit pit index
     * @return stones number
     */
    int captureStones(int pit) {
        requireValidPit(pit);

        int stones = pits[pit];
        pits[pit] = 0;
        return stones;
    }

    /**
     * Capture stones from reversed pit.
     *
     * @param pit pit index
     * @return stones number
     */
    int captureOppositeStones(int pit) {
        return captureStones(getPits().length - pit - 1);
    }

    /**
     * Add one stone to the large pit.
     */
    void addStoneToLargePit() {
        largePit++;
    }

    /**
     * Add stones to the large pit.
     *
     * @param stones stones number
     */
    void addStonesToLargePit(int stones) {
        largePit += stones;
    }

    /**
     * Add one stone to the given pit.
     *
     * @param pit pit index
     */
    void addStoneToSmallPit(int pit) {
        requireValidPit(pit);
        pits[pit]++;
    }

    /**
     * Check whether the given pit is the valid small pit.
     *
     * @param pit pit index
     * @return true if valid otherwise false
     */
    boolean isSmallPit(int pit) {
        return 0 <= pit && pit < pits.length;
    }

    /**
     * Check whether the given pit has 0 stones.
     *
     * @param pit pit index
     * @return true if pit has 0 stones otherwise false
     */
    boolean isEmptyPit(int pit) {
        return pits[pit] == 0;
    }

    /**
     * Check whether it is player's turn or not.
     *
     * @return true if my turn otherwise false
     */
    boolean isMyTurn() {
        return turn;
    }

    /**
     * Check whether all pits are empty or not.
     *
     * @return true if all pits are empty otherwise false
     */
    boolean allPitsAreEmpty() {
        return Arrays.stream(pits).allMatch(pit -> pit == 0);
    }

    /**
     * If stone is 0 then have another turn.
     *
     * @param stones number of stones
     * @return true if player has another turn otherwise false
     */
    boolean hasAnotherTurn(int stones) {
        return turn = stones == 0;
    }

    /**
     * Check whether its a valid id.
     *
     * @param playerId player identity
     * @return true if player identity is valid otherwise false
     */
    boolean hasId(String playerId) {
        return this.getId().equals(playerId);
    }

    int[] getPits() {
        return this.pits;
    }

    int getLargePit() {
        return this.largePit;
    }

    /**
     * Get all stones from small pits plus large pit.
     *
     * @return number of stones
     */
    int getAllStones() {
        return Arrays.stream(pits).sum() + largePit;
    }

    String getId() {
        return this.id;
    }

    /**
     * Make a copy of this class.
     *
     * @return new copy
     */
    Player copy() {
        return new Player(id, Arrays.copyOf(pits, pits.length), largePit, turn);
    }

    void myTurn() {
        this.turn = true;
    }

    void resetTurn() {
        this.turn = false;
    }

    void adjustTurn(Player opponent) {
        if (!isMyTurn()) {
            opponent.myTurn();
        }
    }

    private void requireValidPit(int pit) {
        if (!isSmallPit(pit)) {
            throw new IllegalArgumentException("Pit index is " + pit);
        }
    }


}
