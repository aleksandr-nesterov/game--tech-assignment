package com.bol.tech.assignment.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
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

    public static PlayerState of(Player player) {
        return new PlayerState(player.getId(), player.getPits(), player.getLargePit(), player.isMyTurn());
    }

}
