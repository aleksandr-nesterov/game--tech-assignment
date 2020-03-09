package com.bol.tech.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class GameStateDto {

    private final PlayerDto player;
    private final PlayerDto opponent;
}
