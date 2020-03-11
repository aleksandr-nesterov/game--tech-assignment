package com.bol.tech.assignment.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private static final String PLAYER_NAME = "John";

    Player player;

    @BeforeEach
    void setUp() {
        player = new Player(PLAYER_NAME, new int[]{6, 6, 6, 6, 6, 6}, true);
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
    void expectCaptureStonesFailureOnInvalidPit() {
        assertThrows(IllegalArgumentException.class, () -> player.captureStones(-1));
        assertThrows(IllegalArgumentException.class, () -> player.captureStones(6));
    }

    @Test
    void expectAddingStoneToLargePit() {
        player.addStoneToLargePit();

        assertEquals(1, player.getLargePit());
    }

    @Test
    void expectAddingStonesToLargePit() {
        player.addStonesToLargePit(3);
        player.addStonesToLargePit(4);

        assertEquals(7, player.getLargePit());
    }

    @Test
    void expectAddingStoneToSmallPit() {
        player.addStoneToSmallPit(0);

        assertEquals(7, player.getPits()[0]);
    }

    @Test
    void expectAddStoneToSmallPitFailureOnInvalidPit() {
        assertThrows(IllegalArgumentException.class, () -> player.addStoneToSmallPit(-1));
        assertThrows(IllegalArgumentException.class, () -> player.addStoneToSmallPit(6));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    void expectIsSmallPit(int pit) {
        assertTrue(player.isSmallPit(pit));
    }

    @Test
    void expectEmptyPit() {
        player = new Player(PLAYER_NAME, new int[]{0, 0, 0, 0, 0, 0});

        assertTrue(player.isEmptyPit(4));
    }

    @Test
    void expectAllPitsAreEmpty() {
        player = new Player(PLAYER_NAME, new int[]{0, 0, 0, 0, 0, 0});

        assertTrue(player.allPitsAreEmpty());
    }

    @Test
    void expectToSetAnotherTurn() {
        player = new Player(PLAYER_NAME, new int[]{0, 0, 0, 0, 0, 0});

        assertTrue(player.hasAnotherTurn(0));
    }

    @Test
    void expectPlayerToHaveId() {
        player = new Player(PLAYER_NAME, new int[]{0, 0, 0, 0, 0, 0});

        assertTrue(player.hasId(PLAYER_NAME));
    }

    @Test
    void expect36Stones() {
        assertEquals(36, player.getAllStones());
    }

    @Test
    void expectPlayerCopy() {

        Player copy = player.copy();

        assertNotEquals(System.identityHashCode(player), System.identityHashCode(copy));
    }

    @Test
    void expectTurnSwitch() {
        player = new Player(PLAYER_NAME, new int[]{0, 0, 0, 0, 0, 0});
        Player opponent = new Player("opponent_name", new int[]{6, 6, 6, 6, 6, 6});

        player.adjustTurn(opponent);

        assertFalse(player.isMyTurn());
        assertTrue(opponent.isMyTurn());
    }
}
