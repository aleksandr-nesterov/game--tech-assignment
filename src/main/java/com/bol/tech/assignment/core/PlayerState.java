package com.bol.tech.assignment.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.jcip.annotations.Immutable;

import java.util.Arrays;

/**
 * Represents current player state.
 */
@Getter
@EqualsAndHashCode
@Immutable
public class PlayerState {

    private final String id;
    private final int[] pits;
    private final int largePit;
    private final boolean turn;

    private PlayerState(String id, int[] pits, int largePit, boolean turn) {
        this.id = id;
        this.pits = pits;
        this.largePit = largePit;
        this.turn = turn;
    }

    public int[] getPits() {
        return Arrays.copyOf(pits, pits.length);
    }

    /**
     * Create a player state from {@link Player} player.
     *
     * @param player {@link Player}
     * @return {@link PlayerState}
     */
    static PlayerState of(Player player) {
        return new PlayerState(player.getId(), player.getPits(), player.getLargePit(), player.isMyTurn());
    }

}
