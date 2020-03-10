package com.bol.tech.assignment.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private static final String PLAYER_NAME = "John";
    private static final String OPPONENT_NAME = "Mark";

    Player player;
    Player opponent;
    Game game;

    @BeforeEach
    void setUp() {
        player = new Player(PLAYER_NAME, new int[] {6, 6, 6, 6, 6, 6}, true);
        opponent = new Player(OPPONENT_NAME, new int[] {6, 6, 6, 6, 6, 6});
        game = new Game(player, opponent);
    }

    @Test
    void expectStonesDistributionAcrossPlayerPits() {
        game.playRound(PLAYER_NAME, 0);

        int[] expectedPlayerPits = {0, 7, 7, 7, 7, 7};
        int expectedLargePit = 1;

        assertEquals(expectedLargePit, player.getLargePit());
        assertArrayEquals(expectedPlayerPits, player.getPits());
    }

    @Test
    void expectStonesDistributionAcrossPlayerAndOpponentPits() {
        game.playRound(PLAYER_NAME, 3);

        int[] expectedPlayerPits = {6, 6, 6, 0, 7, 7};

        assertEquals(1, player.getLargePit());
        assertArrayEquals(expectedPlayerPits, player.getPits());

        int[] expectedOpponentPits = {7, 7, 7, 6, 6, 6};

        assertEquals(0, opponent.getLargePit());
        assertArrayEquals(expectedOpponentPits, opponent.getPits());
    }

    @Test
    void expectStonesDistributionAcrossAllPits() {
        int[] playerPits = {72, 0, 0, 0, 0, 0};
        int[] opponentPits = {0, 0, 0, 0, 0, 0};

        player = new Player(PLAYER_NAME, playerPits);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        game = new Game(player, opponent);

        game.playRound(PLAYER_NAME, 0);

        int[] expectedPlayerPits = {5, 6, 6, 6, 6, 6};

        assertEquals(6, player.getLargePit());
        assertArrayEquals(expectedPlayerPits, player.getPits());

        int[] expectedOpponentPits = {6, 5, 5, 5, 5, 5};

        assertEquals(0, opponent.getLargePit());
        assertArrayEquals(expectedOpponentPits, opponent.getPits());
    }

    @Test
    void expectPlayerTurn() {

        game.playRound(PLAYER_NAME, 0);

        assertTrue(player.isMyTurn());
        assertFalse(opponent.isMyTurn());
    }

    @Test
    void expectOpponentTurn() {

        game.playRound(PLAYER_NAME, 3);

        assertTrue(opponent.isMyTurn());
        assertFalse(player.isMyTurn());
    }

    @Test
    void expectPlayerAndOpponentStonesToBeMoved_IfLastStoneLandsInEmptyPit() {
        int[] playerPits = {4, 6, 6, 6, 0, 6};
        int[] opponentPits = {8, 8, 8, 8, 6, 6};

        player = new Player(PLAYER_NAME, playerPits, true);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        game = new Game(player, opponent);

        game.playRound(PLAYER_NAME, 0);

        int[] expectedPlayerPits = {0, 7, 7, 7, 0, 6};

        assertEquals(7, player.getLargePit());
        assertArrayEquals(expectedPlayerPits, player.getPits());

        int[] expectedOpponentPits = {8, 8, 8, 8, 0, 6};

        assertArrayEquals(expectedOpponentPits, opponent.getPits());
    }

    @Test
    void expectTurnSwitch_IfLastStoneLandsInEmptyPit() {
        int[] playerPits = {4, 6, 6, 6, 0, 6};
        int[] opponentPits = {8, 8, 8, 8, 6, 6};

        player = new Player(PLAYER_NAME, playerPits, true);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        game = new Game(player, opponent);

        game.playRound(PLAYER_NAME, 0);

        assertFalse(player.isMyTurn());
        assertTrue(opponent.isMyTurn());
    }

    @Test
    void expectGameOverEmptyPlayerPits() {
        int[] playerPits = {0, 0, 0, 0, 0, 0};
        int[] opponentPits = {6, 6, 6, 6, 6, 6};

        player = new Player(PLAYER_NAME, playerPits);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        game = new Game(player, opponent);

        GameState gameState = game.playRound(PLAYER_NAME, 0);

        assertNotNull(gameState.getResult());
    }

    @Test
    void expectGameOverEmptyOpponentPits() {
        int[] playerPits = {6, 6, 6, 6, 6, 6};
        int[] opponentPits = {0, 0, 0, 0, 0, 0};

        player = new Player(PLAYER_NAME, playerPits);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        game = new Game(player, opponent);

        GameState gameState = game.playRound(PLAYER_NAME, 0);

        assertNotNull(gameState.getResult());
    }

    @Test
    void expectGameIsNotOverOnNonEmptyPits() {
        int[] playerPits = {0, 0, 0, 0, 0, 6};
        int[] opponentPits = {6, 0, 0, 0, 0, 0};

        player = new Player(PLAYER_NAME, playerPits);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        game = new Game(player, opponent);

        GameState gameState = game.playRound(PLAYER_NAME, 0);

        assertNull(gameState.getResult());
    }

    @Test
    void expectPlayerToBeTheWinner() {
        int[] playerPits = {0, 0, 0, 0, 0, 0};
        int[] opponentPits = {6, 6, 6, 6, 6, 4};

        player = new Player(PLAYER_NAME, playerPits);
        player.addStonesToLargePit(38);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        game = new Game(player, opponent);

        GameState gameState = game.playRound(PLAYER_NAME, 0);

        PlayerState expectedWinner = PlayerState.of(player);

        assertEquals(expectedWinner, gameState.getResult().getWinner());
    }

    @Test
    void expectOpponentToBeTheWinner() {
        int[] playerPits = {6, 6, 6, 0, 0, 0};
        int[] opponentPits = {0, 0, 0, 0, 0, 0};

        player = new Player(PLAYER_NAME, playerPits);
        player.addStonesToLargePit(17);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        opponent.addStonesToLargePit(37);
        game = new Game(player, opponent);

        GameState gameState = game.playRound(OPPONENT_NAME, 0);

        PlayerState expectedWinner = PlayerState.of(opponent);

        assertEquals(expectedWinner, gameState.getResult().getWinner());
    }

    @Test
    void expectTie() {
        int[] playerPits = {0, 0, 0, 0, 0, 0};
        int[] opponentPits = {0, 0, 4, 6, 6, 4};

        player = new Player(PLAYER_NAME, playerPits);
        player.addStonesToLargePit(36);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        opponent.addStonesToLargePit(16);
        game = new Game(player, opponent);

        GameState gameState = game.playRound(PLAYER_NAME, 0);

        assertTrue(gameState.getResult().isTie());
        assertNull(gameState.getResult().getWinner());
    }

    @Test
    void expectGameStatePlayerCopy() {

        GameState state = game.getState();

        assertNotEquals(System.identityHashCode(player), System.identityHashCode(state.getPlayer()));
        assertNotEquals(System.identityHashCode(opponent), System.identityHashCode(state.getOpponent()));
    }

    @Test
    void expectFailureOnNullPlayer() {
        assertThrows(NullPointerException.class, () -> game.playRound(null, 0));
    }

    @Test
    void expectFailureOnInvalidPlayerId() {
        assertThrows(IllegalArgumentException.class, () -> game.playRound("invalidPlayerId", 0));
    }

}
