package com.bol.tech.assignment.controllers;

import com.bol.tech.assignment.core.Game;
import com.bol.tech.assignment.core.GameState;
import com.bol.tech.assignment.dto.GameRoundRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {

    private final Game game;

    @PutMapping
    public GameState playNextRound(@RequestBody GameRoundRequest gameRoundRequest) {
        return game.playRound(gameRoundRequest.getPlayerId(), gameRoundRequest.getPit() - 1);
    }

    @GetMapping
    public GameState getGameState() {
        return game.getState();
    }

}
