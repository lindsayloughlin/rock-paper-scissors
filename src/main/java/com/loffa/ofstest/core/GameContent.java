package com.loffa.ofstest.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.loffa.ofstest.core.enums.GameResultType;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class GameContent {

    @JsonProperty
    private Integer id;
    // TODO: make an array Of players so we can play with more than two people?
    @JsonProperty("player_one_move")
    public final MoveMade playerOneMove;
    @JsonProperty("player_two_move")
    public final MoveMade playerTwoMove;
    @JsonProperty("player_one_result")
    public final GameResultType resultType;

//    public String getHelloworld() {
//        return helloworld;
//    }

    //public final String helloworld = "testusername";

    public DateTime getGameTime() {
        return gameTime;
    }

    @JsonIgnore
    public String getPlayerOneUsername() {
        if (playerOneMove != null) {
            return playerOneMove.username;
        }
        return null;
    }

    @JsonIgnore
    public String getPlayerOneMoveType() {
        if (playerOneMove != null) {
            return playerOneMove.moveType.name();
        }
        return null;
    }

    @JsonIgnore
    public String getPlayerTwoUsername() {
        if (playerTwoMove != null) {
            return playerTwoMove.username;
        }
        return null;
    }

    @JsonIgnore
    public String getPlayerTwoMoveType() {
        if (playerTwoMove != null) {
            return playerTwoMove.moveType.name();
        }
        return null;
    }

    @JsonIgnore
    public String getPlayerOneResult() {
        if (resultType != null) {
            return resultType.name();
        }
        return null;
    }

    @JsonProperty("game_time")
    public final DateTime gameTime;

    private GameContent(Builder builder) {
        setId(builder.id);
        playerOneMove = builder.playerOneMove;
        playerTwoMove = builder.playerTwoMove;
        resultType = builder.resultType;
        gameTime = builder.gameTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GameContent(@JsonProperty("player_one_move") MoveMade playerOneMove,
                       @JsonProperty("player_two_move") MoveMade playerTwoMove,
                       @JsonProperty("player_one_result") GameResultType resultType,
                       @JsonProperty("game_time") DateTime gameTime) {
        this.playerOneMove = playerOneMove;
        this.playerTwoMove = playerTwoMove;
        this.resultType = resultType;
        this.gameTime = gameTime;
    }

    @JsonIgnore
    public MoveMade[] getPlayersMoves() {
        return new MoveMade[] { playerOneMove, playerTwoMove };
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameContent content = (GameContent) o;

        if (id != null ? !id.equals(content.id) : content.id != null) return false;
        if (playerOneMove != null ? !playerOneMove.equals(content.playerOneMove) : content.playerOneMove != null)
            return false;
        if (playerTwoMove != null ? !playerTwoMove.equals(content.playerTwoMove) : content.playerTwoMove != null)
            return false;
        if (resultType != content.resultType) return false;
        return !(gameTime != null ? !gameTime.equals(content.gameTime) : content.gameTime != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (playerOneMove != null ? playerOneMove.hashCode() : 0);
        result = 31 * result + (playerTwoMove != null ? playerTwoMove.hashCode() : 0);
        result = 31 * result + (resultType != null ? resultType.hashCode() : 0);
        result = 31 * result + (gameTime != null ? gameTime.hashCode() : 0);
        return result;
    }

    public static final class Builder {
        private Integer id;
        private MoveMade playerOneMove;
        private MoveMade playerTwoMove;
        private GameResultType resultType;
        private DateTime gameTime;

        private Builder() {
        }

        public Builder withId(Integer val) {
            id = val;
            return this;
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

        public Builder withGameTime(DateTime val) {
            gameTime = val;
            return this;
        }

        public GameContent build() {
            return new GameContent(this);
        }
    }
}

