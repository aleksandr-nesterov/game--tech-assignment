package com.bol.tech.assignment.dto;

import com.bol.tech.assignment.core.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerDto {

    private final String id;
    private final int[] pits;

    public static PlayerDto of(Player player) {
        return new PlayerDto(player.getId(), player.getPits());
    }
}
