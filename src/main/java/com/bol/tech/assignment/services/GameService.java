package com.bol.tech.assignment.services;

import com.bol.tech.assignment.core.Game;
import com.bol.tech.assignment.core.GameState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GameService {

    private final Game game;

    public GameState playNextRound(String playerId, int pit) {
        return game.playRound(playerId, pit);
    }

}
