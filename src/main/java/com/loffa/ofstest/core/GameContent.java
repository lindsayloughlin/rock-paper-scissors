package com.loffa.ofstest.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loffa.ofstest.core.enums.GameResultType;
import org.joda.time.DateTime;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class GameContent {

    // TODO: make an array Of Players.
    @JsonProperty("player_one_move")
    MoveMade playerOneMove;
    @JsonProperty("player_two_move")
    MoveMade playerTwoMove;
    @JsonProperty("player_one_result")
    GameResultType resultType;
    @JsonProperty("game_time")
    DateTime gameTime;

    public GameContent(@JsonProperty("player_one_move") MoveMade playerOneMove,
                       @JsonProperty("player_two_move") MoveMade playerTwoMove,
                       @JsonProperty("player_one_result") GameResultType resultType,
                       @JsonProperty("game_time") DateTime gameTime) {
        this.playerOneMove = playerOneMove;
        this.playerTwoMove = playerTwoMove;
        this.resultType = resultType;
        this.gameTime = gameTime;
    }

    private GameContent(Builder builder) {
        playerOneMove = builder.playerOneMove;
        playerTwoMove = builder.playerTwoMove;
        resultType = builder.resultType;
        gameTime = builder.gameTime;
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

        public Builder withPlayerOneMove(MoveMade val) {
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
