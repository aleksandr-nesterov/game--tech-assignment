package com.bol.tech.assignment.dto;

import com.bol.tech.assignment.core.GameState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameRoundResponse {

    private final GameState result;

}
