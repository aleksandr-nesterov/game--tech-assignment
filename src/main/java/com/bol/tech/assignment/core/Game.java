package com.bol.tech.assignment.core;

import net.jcip.annotations.ThreadSafe;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.bol.tech.assignment.core.GameState.*;
import static com.bol.tech.assignment.core.GameState.Result.tie;
import static com.bol.tech.assignment.core.GameState.Result.winnerIs;

@ThreadSafe
public class Game {

    private final Player originalPlayer;
    private final Player originalOpponent;

    private Player player;
    private Player opponent;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();
    private final Lock readLock = lock.readLock();

    /**
     * Create a game with 2 players. First player has the turn.
     *
     * @param player1Id player 1 id
     * @param player2Id player 2 id
     */
    public Game(String player1Id, String player2Id) {
        this(new Player(player1Id, true), new Player(player2Id));
    }

    Game(Player player, Player opponent) {
        this.player = player;
        this.opponent = opponent;
        this.originalPlayer = player.copy();
        this.originalOpponent = opponent.copy();
    }

    /**
     * Play game round with the given player identity and pit index.
     *
     * <p>
     * The rules are:
     * -- capture player stones from the given pit
     * -- move stones across the player and opponent pits
     * -- if game is over -> return the winner
     * otherwise -> return the game state
     *
     * @param playerId player identity
     * @param pit      pit index
     * @return {@link GameState}
     */
    public GameState playRound(String playerId, int pit) {
        writeLock.lock();
        try {
            requireValidPlayerId(playerId);

            Player player = findPlayerBy(playerId);
            Player opponent = findOpponentBy(playerId);

            int stones = player.captureStones(pit);

            moveStones(stones, pit + 1, player, opponent);

            if (isGameOver()) {
                resetTurn();
                return gameOver(chooseWinner());
            }

            player.adjustTurn(opponent);

            return nextRound();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Return the game state. Only read operations are allowed here.
     *
     * @return {@link GameState}
     */
    public GameState getState() {
        readLock.lock();
        try {
            if (isGameOver()) {
                return gameOver(chooseWinner());
            }
            return gameState(player.copy(), opponent.copy());
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Reset the game state to original state.
     */
    public void resetState() {
        writeLock.lock();
        try {
            this.player = originalPlayer.copy();
            this.opponent = originalOpponent.copy();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Return the game state with the result.
     *
     * @param result result of the game
     * @return {@link GameState}
     */
    private GameState gameOver(Result result) {
        return gameState(player.copy(), opponent.copy(), result);
    }

    /**
     * Check whether the game is over or not. The game is over when one of the players have empty pits.
     *
     * @return true if game is over otherwise false.
     */
    private boolean isGameOver() {
        return player.allPitsAreEmpty() || opponent.allPitsAreEmpty();
    }

    /**
     * Choose the winner of the game. The winner is the player who has the most stones.
     *
     * @return {@link Result}
     */
    private Result chooseWinner() {
        int playerStones = player.getAllStones();
        int opponentStones = opponent.getAllStones();

        if (playerStones > opponentStones) {
            return winnerIs(player.copy());
        }

        if (playerStones < opponentStones) {
            return winnerIs(opponent.copy());
        }

        return tie();
    }

    /**
     * Return the current state of the game for the next round.
     *
     * @return {@link GameState}
     */
    private GameState nextRound() {
        return gameState(this.player.copy(), this.opponent.copy());
    }

    private void requireValidPlayerId(String playerId) {
        Objects.requireNonNull(playerId, "Player can not be null");

        if (!player.hasId(playerId) && !opponent.hasId(playerId)) {
            throw new IllegalArgumentException("Invalid playerId: " + playerId);
        }
    }

    private Player findPlayerBy(String playerId) {
        if (player.hasId(playerId)) {
            return player;
        }
        return opponent;
    }

    private Player findOpponentBy(String playerId) {
        if (player.hasId(playerId)) {
            return opponent;
        }
        return player;
    }

    /**
     * Reset next turn of the players.
     */
    private void resetTurn() {
        player.resetTurn();
        opponent.resetTurn();
    }

    /**
     * Move stones from the given pit across player and opponent pits.
     *
     * @param stones   number of stones
     * @param pit      starting pit index
     * @param player   player
     * @param opponent opponent
     */
    private static void moveStones(int stones, int pit, Player player, Player opponent) {

        if (stones <= 0) {
            return;
        }

        int stonesLeft = movePlayerStones(stones, pit, player, opponent);

        stonesLeft = moveOpponentStones(stonesLeft, opponent);

        moveStones(stonesLeft, 0, player, opponent);
    }

    /**
     * Move stones across player pits.
     * If last stone lands in the empty pit then capture stones from opponent's opposite pit
     * and add them to the player's large it.
     *
     * @param stones   number of stones
     * @param pit      pit index
     * @param player   player
     * @param opponent opponent
     * @return stones left
     */
    private static int movePlayerStones(int stones, int pit, Player player, Player opponent) {

        while (player.isSmallPit(pit) && hasStones(stones)) {

            if (lastStone(stones) && player.isEmptyPit(pit)) {
                player.addStonesToLargePit(stones + opponent.captureOppositeStones(pit));
                player.resetTurn();
                return 0;
            }

            player.addStoneToSmallPit(pit++);
            stones--;
        }

        if (hasStones(stones--)) {
            player.addStoneToLargePit();
        }

        player.hasAnotherTurn(stones);

        return stones;
    }

    /**
     * Move stones across opponent pits.
     *
     * @param stones   number of stones
     * @param opponent opponent
     * @return stones left
     */
    private static int moveOpponentStones(int stones, Player opponent) {
        int pit = 0;
        while (opponent.isSmallPit(pit) && hasStones(stones)) {
            opponent.addStoneToSmallPit(pit++);
            stones--;
        }
        return stones;
    }

    private static boolean lastStone(int stones) {
        return stones == 1;
    }

    private static boolean hasStones(int stones) {
        return stones > 0;
    }

}
