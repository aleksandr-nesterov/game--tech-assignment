package com.bol.tech.assignment.core;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class GameState {

    private final Player player;
    private final Player opponent;
    private final Result result;

    private GameState(Player player, Player opponent) {
        this.player = player;
        this.opponent = opponent;
        this.result = null;
    }

    private GameState(Result result) {
        this.player = null;
        this.opponent = null;
        this.result = result;
    }

    public static GameState gameState(Player player, Player opponent) {
        return new GameState(player.copy(), opponent.copy());
    }

    public static GameState gameOver(Result result) {
        return new GameState(result);
    }

    @Getter
    @ToString
    public static class Result {

        private final boolean tie;
        private final Player winner;

        private Result(boolean tie) {
            this.tie = tie;
            this.winner = null;
        }

        private Result(Player winner) {
            this.winner = winner;
            this.tie = false;
        }

        public static Result tie() {
            return new Result(true);
        }

        public static Result winnerIs(Player winner) {
            return new Result(winner.copy());
        }
    }

}
