package com.loffa.ofstest.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loffa.ofstest.core.enums.MoveType;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class MoveMade {

    @JsonProperty("username")
    public final String username;

    @JsonProperty("move")
    public final MoveType moveType;

    public MoveMade(@JsonProperty("username") String username,@JsonProperty("move") MoveType moveMake) {
        this.username = username;
        this.moveType = moveMake;
    }

    private MoveMade(Builder builder) {
        username = builder.username;
        moveType = builder.moveMake;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String username;
        private MoveType moveMake;

        private Builder() {
        }

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withMoveType(MoveType val) {
            moveMake = val;
            return this;
        }

        public MoveMade build() {
            return new MoveMade(this);
        }
    }
}
