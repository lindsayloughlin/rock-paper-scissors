package com.loffa.ofstest.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lloughlin on 13/09/2016.
 */
public class HighScore {

    @JsonProperty("username")
    public final String username;

    @JsonProperty("wins")
    public final Integer wins;

    private HighScore(Builder builder) {
        username = builder.username;
        wins = builder.win;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getUsername() {
        return username;
    }

    public Integer getWins() {
        return wins;
    }

    public static final class Builder {
        private String username;
        private Integer win;

        private Builder() {
        }

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withWin(Integer val) {
            win = val;
            return this;
        }

        public HighScore build() {
            return new HighScore(this);
        }
    }
}
