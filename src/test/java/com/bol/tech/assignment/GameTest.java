package com.bol.tech.assignment;

import com.bol.tech.assignment.core.Game;
import com.bol.tech.assignment.core.Player;
import com.bol.tech.assignment.core.GameState;
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
        player = new Player(PLAYER_NAME, new int[] {6, 6, 6, 6, 6, 6});
        opponent = new Player(OPPONENT_NAME, new int[] {6, 6, 6, 6, 6, 6});
        game = new Game(player, opponent);
    }

    @Test
    void expectToCaptureStones() {
        int startPit = 0;
        int actualStones = player.captureStones(startPit);

        int[] expectedPlayerPits = {0, 6, 6, 6, 6, 6};
        int expectedStones = 6;

        assertEquals(expectedStones, actualStones);
        assertArrayEquals(expectedPlayerPits, player.getPits());

    }

    @Test
    void expectStonesDistributionAcrossPlayerPits() {
        moveStonesFromPit(0);

        int[] expectedPlayerPits = {0, 7, 7, 7, 7, 7};
        int expectedLargePit = 1;

        assertEquals(expectedLargePit, player.getLargePit());
        assertArrayEquals(expectedPlayerPits, player.getPits());
    }

    @Test
    void expectStonesDistributionAcrossPlayerAndOpponentPits() {
        moveStonesFromPit(3);

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

        moveStonesFromPit(0);

        int[] expectedPlayerPits = {5, 6, 6, 6, 6, 6};

        assertEquals(6, player.getLargePit());
        assertArrayEquals(expectedPlayerPits, player.getPits());

        int[] expectedOpponentPits = {6, 5, 5, 5, 5, 5};

        assertEquals(0, opponent.getLargePit());
        assertArrayEquals(expectedOpponentPits, opponent.getPits());
    }

    @Test
    void expectPlayerTurn() {

        moveStonesFromPit(0);

        assertTrue(player.isMyTurn());
    }

    @Test
    void expectPlayerAndOpponentStonesToBeMoved_IfLastStoneLandsInEmptyPit() {
        int[] playerPits = {4, 6, 6, 6, 0, 6};
        int[] opponentPits = {8, 8, 8, 8, 6, 6};

        player = new Player(PLAYER_NAME, playerPits);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        game = new Game(player, opponent);

        moveStonesFromPit(0);

        int[] expectedPlayerPits = {0, 7, 7, 7, 0, 6};

        assertEquals(7, player.getLargePit());
        assertArrayEquals(expectedPlayerPits, player.getPits());

        int[] expectedOpponentPits = {8, 8, 8, 8, 0, 6};

        assertArrayEquals(expectedOpponentPits, opponent.getPits());
    }

    @Test
    void expectGameOverEmptyPlayerPits() {
        int[] playerPits = {0, 0, 0, 0, 0, 0};
        int[] opponentPits = {6, 6, 6, 6, 6, 6};

        player = new Player(PLAYER_NAME, playerPits);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        game = new Game(player, opponent);

        assertTrue(game.isGameOver());
    }

    @Test
    void expectGameOverEmptyOpponentPits() {
        int[] playerPits = {6, 6, 6, 6, 6, 6};
        int[] opponentPits = {0, 0, 0, 0, 0, 0};

        player = new Player(PLAYER_NAME, playerPits);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        game = new Game(player, opponent);

        assertTrue(game.isGameOver());
    }

    @Test
    void expectGameIsNotOverOnNonEmptyPits() {
        int[] playerPits = {0, 0, 0, 0, 0, 6};
        int[] opponentPits = {6, 0, 0, 0, 0, 0};

        player = new Player(PLAYER_NAME, playerPits);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        game = new Game(player, opponent);

        assertFalse(game.isGameOver());
    }

    @Test
    void expectPlayerToBeTheWinner() {
        int[] playerPits = {0, 0, 0, 0, 0, 0};
        int[] opponentPits = {6, 6, 6, 6, 6, 4};

        player = new Player(PLAYER_NAME, playerPits);
        player.addStonesToLargePit(38);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        game = new Game(player, opponent);

        GameState.Result actualResult = game.chooseWinner();

        assertEquals(player, actualResult.getWinner());
    }

    @Test
    void expectOpponentToBeTheWinner() {
        int[] playerPits = {6, 6, 6, 0, 0, 0};
        int[] opponentPits = {0, 0, 4, 6, 6, 4};

        player = new Player(PLAYER_NAME, playerPits);
        player.addStonesToLargePit(17);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        opponent.addStonesToLargePit(17);
        game = new Game(player, opponent);

        GameState.Result actualResult = game.chooseWinner();

        assertEquals(opponent, actualResult.getWinner());
    }

    @Test
    void expectTie() {
        int[] playerPits = {6, 6, 6, 0, 0, 0};
        int[] opponentPits = {0, 0, 4, 6, 6, 4};

        player = new Player(PLAYER_NAME, playerPits);
        player.addStonesToLargePit(18);
        opponent = new Player(OPPONENT_NAME, opponentPits);
        opponent.addStonesToLargePit(16);
        game = new Game(player, opponent);

        GameState.Result actualResult = game.chooseWinner();

        assertTrue(actualResult.isTie());
        assertNull(actualResult.getWinner());
    }

    private void moveStonesFromPit(int startPit) {
        int stones = player.captureStones(startPit);
        int nextPit = startPit + 1;

        game.moveStones(stones, nextPit);
    }

}
