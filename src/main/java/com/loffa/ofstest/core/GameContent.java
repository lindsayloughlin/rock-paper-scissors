package com.loffa.ofstest.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loffa.ofstest.core.enums.GameResultType;
import org.joda.time.DateTime;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class GameContent {

    // TODO: make an array Of players so we can play with more than two people?
    @JsonProperty("player_one_move")
    public final MoveMade playerOneMove;
    @JsonProperty("player_two_move")
    public final MoveMade playerTwoMove;
    @JsonProperty("player_one_result")
    public final GameResultType resultType;
    @JsonProperty("game_time")
    public final DateTime gameTime;

    public GameContent(@JsonProperty("player_one_move") MoveMade playerOneMove,
                       @JsonProperty("player_two_move") MoveMade playerTwoMove,
                       @JsonProperty("player_one_result") GameResultType resultType,
                       @JsonProperty("game_time") DateTime gameTime) {
        this.playerOneMove = playerOneMove;
        this.playerTwoMove = playerTwoMove;
        this.resultType = resultType;
        this.gameTime = gameTime;
    }

    public MoveMade[] getPlayersMoves() {
        return new MoveMade[] { playerOneMove, playerTwoMove };
    }

    private GameContent(Builder builder) {
        playerOneMove = builder.playerOneMove;
        playerTwoMove = builder.playerTwoMove;
        resultType = builder.resultType;
        gameTime = builder.gameTime != null ? builder.gameTime : DateTime.now();
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private MoveMade playerOneMove;
        private MoveMade playerTwoMove;
        private GameResultType resultType;
        private DateTime gameTime;

        private Builder() {
        }

        public Builder  withPlayerOneMove(MoveMade val) {
            playerOneMove = val;
            return this;
        }

        public Builder withPlayerTwoMove(MoveMade val) {
            playerTwoMove = val;
            return this;
        }

        public Builder withResultType(GameResultType val) {
            resultType = val;
            return this;
        }
        public Builder withGameType(DateTime val) {
            gameTime = val;
            return this;
        }

        public GameContent build() {
            return new GameContent(this);
        }
    }
}

