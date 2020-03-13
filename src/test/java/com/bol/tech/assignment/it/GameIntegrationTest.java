package com.bol.tech.assignment.it;

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

}
