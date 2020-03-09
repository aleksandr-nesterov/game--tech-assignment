package com.bol.tech.assignment.core;

import org.springframework.beans.factory.annotation.Value;

import static com.bol.tech.assignment.core.GameState.*;
import static com.bol.tech.assignment.core.GameState.Result.tie;
import static com.bol.tech.assignment.core.GameState.Result.winnerIs;

public class Game {

//    private final String player1Id;
//    private final String player2Id;

    private Player player;
    private Player opponent;

    public Game(@Value("${game.player1.id}") String player1Id, @Value("${game.player2.id}") String player2Id) {
//        this.player1Id = player1Id;
//        this.player2Id = player2Id;
        this.player = new Player(player1Id);
        this.opponent = new Player(player2Id);
    }

    public Game(Player player, Player opponent) {
        this.player = player.copy();
        this.opponent = opponent.copy();
    }

    private void swapPlayers() {
        Player temp = player;
        player = opponent;
        opponent = temp;
    }

    public GameState playRound(String playerId, int pit) {

        int stones = player.captureStones(pit);

        moveStones(stones, pit + 1);

        if (isGameOver()) {
            return gameOver(chooseWinner());
        }

        return gameState(player, opponent);
    }

    public void moveStones(int stones, int pit) {

        if (stones <= 0) {
            return;
        }

        int stonesLeft = movePlayerStones(stones, pit);

        player.hasAnotherTurn(stonesLeft);

        stonesLeft = moveOpponentStones(stonesLeft);

        moveStones(stonesLeft, 0);
    }

    public boolean isGameOver() {
        return player.allPitsAreEmpty() || opponent.allPitsAreEmpty();
    }

    public Result chooseWinner() {
        int playerStones = player.getAllStones();
        int opponentStones = opponent.getAllStones();

        if (playerStones > opponentStones) {
            return winnerIs(player);
        }

        if (playerStones < opponentStones) {
            return winnerIs(opponent);
        }

        return tie();
    }

    public GameState getState() {
        return gameState(player, opponent);
    }

    private int movePlayerStones(int stones, int pit) {

        while (player.isSmallPit(pit) && stones > 0) {

            if (lastStone(stones) && player.isEmptyPit(pit)) {
                player.addStonesToLargePit(stones + opponent.captureStones(pit));
                return 0;
            }

            player.addStoneToSmallPit(pit++);
            stones--;
        }

        if (stones-- > 0) {
            player.addStoneToLargePit();
        }

        return stones;
    }

    private int moveOpponentStones(int stones) {
        int pit = 0;
        while (opponent.isSmallPit(pit) && stones-- > 0) {
            opponent.addStoneToSmallPit(pit++);
        }
        return stones;
    }

    private boolean lastStone(int stones) {
        return stones == 1;
    }
}
