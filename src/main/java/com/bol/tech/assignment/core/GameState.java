package com.bol.tech.assignment.core;

import lombok.Getter;
import lombok.ToString;

import static com.bol.tech.assignment.core.PlayerState.of;

/**
 * Represents current game state.
 */
@Getter
public class GameState {

    private final PlayerState player;
    private final PlayerState opponent;
    private final Result result;

    private GameState(PlayerState player, PlayerState opponent) {
        this(player, opponent, null);
    }

    private GameState(PlayerState player, PlayerState opponent, Result result) {
        this.player = player;
        this.opponent = opponent;
        this.result = result;
    }

    static GameState gameState(Player player, Player opponent) {
        return new GameState(of(player), of(opponent));
    }

    static GameState gameState(Player player, Player opponent, Result result) {
        return new GameState(of(player), of(opponent), result);
    }

    @Getter
    @ToString
    public static class Result {

        private final boolean tie;
        private final PlayerState winner;

        private Result(boolean tie) {
            this.tie = tie;
            this.winner = null;
        }

        private Result(PlayerState winner) {
            this.winner = winner;
            this.tie = false;
        }

        static Result tie() {
            return new Result(true);
        }

        static Result winnerIs(Player winner) {
            return new Result(of(winner));
        }
    }

}
