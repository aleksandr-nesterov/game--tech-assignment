package com.bol.tech.assignment.it.support;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class PlayerState {

    private final String id;
    private final int[] pits;
    private final int largePit;
    private final boolean turn;

}
