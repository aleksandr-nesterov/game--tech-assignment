package com.bol.tech.assignment.it.support;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GameState {

    private final PlayerState player;
    private final PlayerState opponent;
    private final Result result;

    @Getter
    @Builder
    public static class Result {

        private final boolean tie;
        private final PlayerState winner;

    }

}
