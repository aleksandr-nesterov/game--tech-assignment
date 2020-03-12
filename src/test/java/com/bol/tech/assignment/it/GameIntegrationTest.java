package com.bol.tech.assignment.it;

import com.bol.tech.assignment.dto.GameRoundRequest;
import com.bol.tech.assignment.it.support.GameRequest;
import com.bol.tech.assignment.it.support.GameState;
import com.bol.tech.assignment.it.support.PlayerState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameIntegrationTest {

    private static final String PLAYER1_ID = "player1";
    private static final String PLAYER2_ID = "player2";

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc client;

    @Test
    @DirtiesContext
    void expectStatus200() throws Exception {
        GameRequest request = new GameRequest();
        request.setPlayerId(PLAYER1_ID);
        request.setPit(String.valueOf(1));

        client.perform(put("/game")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(createResponse()));

    }

    @Test
    @DirtiesContext
    void expectStatus400OnInvalidPlayerId() throws Exception {
        GameRequest request = new GameRequest();
        request.setPlayerId("invalid-playerid");
        request.setPit(String.valueOf(1));

        client.perform(put("/game")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DirtiesContext
    void expectStatus400OnInvalidIntegerPit() throws Exception {
        GameRequest request = new GameRequest();
        request.setPlayerId(PLAYER1_ID);
        request.setPit(String.valueOf(7));

        client.perform(put("/game")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DirtiesContext
    void expectStatus400OnInvalidStringPit() throws Exception {
        GameRequest request = new GameRequest();
        request.setPlayerId(PLAYER1_ID);
        request.setPit("abc");

        client.perform(put("/game")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

//    @Test
//    @DirtiesContext
//    void checkGameTie() throws Exception {
//        simulateGameTie();
//
//        GameRoundRequest request = new GameRoundRequest(5, PLAYER1_ID, PLAYER2_ID);
//
//        client.perform(put("/game/next-round")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(content().string(createTieResponse()));
//
//    }
//
//    @Test
//    @DirtiesContext
//    void checkGameWinner() throws Exception {
//        simulateGameWinner();
//
//        GameRoundRequest request = new GameRoundRequest(5, PLAYER1_ID, PLAYER2_ID);
//
//        client.perform(put("/game/next-round")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(content().string(createWinnerResponse()));
//
//    }

//    private void simulateGameTie() {
//        simulateGameResult(35);
//    }
//
//    private void simulateGameWinner() {
//        simulateGameResult(36);
//    }
//
//    private void simulateGameResult(int largePitStones) {
//        Player player = gameController.getPlayerMap().get(PLAYER1_ID);
//        int[] pits = player.getPits();
//        for (int i = 0; i < pits.length; i++) {
//            pits[i] = 0;
//        }
//        pits[pits.length - 1] = 1;
//        player.addStonesToLargePit(largePitStones);
//    }

    private String createResponse() throws JsonProcessingException {
        PlayerState player = PlayerState.builder()
                .id(PLAYER1_ID)
                .largePit(1)
                .pits(new int[]{0, 7, 7, 7, 7, 7})
                .turn(true)
                .build();
        PlayerState opponent = PlayerState.builder()
                .id(PLAYER2_ID)
                .largePit(0)
                .pits(new int[] {6, 6, 6, 6, 6, 6})
                .turn(false)
                .build();
        return objectMapper.writeValueAsString(GameState.builder().player(player).opponent(opponent).build());
    }
//
//    private String createTieResponse() throws JsonProcessingException {
//        return objectMapper.writeValueAsString(new GameRoundResponse(Result.tie()));
//    }
//
//    private String createWinnerResponse() throws JsonProcessingException {
//        Player player = new Player(PLAYER1_ID, new int[] {0, 0, 0, 0, 0, 0});
//        player.addStonesToLargePit(37);
//        player.hasAnotherTurn(0);
//        return objectMapper.writeValueAsString(new GameRoundResponse(Result.winnerIs(player)));
//    }
}
