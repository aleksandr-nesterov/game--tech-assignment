package com.bol.tech.assignment.core;

import lombok.Getter;
import net.jcip.annotations.Immutable;

import static com.bol.tech.assignment.core.PlayerState.of;

/**
 * Represents current game state.
 */
@Getter
@Immutable
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

    /**
     * Create a game state without result as the game still in progress.
     *
     * @param player {@link Player}
     * @param opponent {@link Player}
     * @return {@link GameState}
     */
    static GameState gameState(Player player, Player opponent) {
        return new GameState(of(player), of(opponent));
    }

    /**
     * Create a game state with the result.
     *
     * @param player {@link Player}
     * @param opponent {@link Player}
     * @param result {@link Result}
     * @return {@link GameState}
     */
    static GameState gameState(Player player, Player opponent, Result result) {
        return new GameState(of(player), of(opponent), result);
    }

    @Getter
    @Immutable
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

        /**
         * Result of the game is - tie.
         *
         * @return {@link Result}
         */
        static Result tie() {
            return new Result(true);
        }

        /**
         * Create result of the game with the given player.
         *
         * @param winner winner of the game
         * @return {@link Result}
         */
        static Result winnerIs(Player winner) {
            return new Result(of(winner));
        }
    }

}
