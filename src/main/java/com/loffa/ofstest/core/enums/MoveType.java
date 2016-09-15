package com.loffa.ofstest.core.enums;

/**
 * Created by lloughlin on 11/09/2016.
 */
public enum MoveType {
    Paper,
    Scissors,
    Rock;

    public MoveType getWinningMove() {
        if (this == Paper) return Scissors;
        if (this == Scissors) return Rock;
        return Paper;
    }
}


