package com.loffa.ofstest.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class Player {

    @JsonCreator
    public Player(@JsonProperty("username") String username, @JsonProperty("password")String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @JsonProperty("username")
    public final String username;

    @JsonProperty("password")
    public final String password;

    private Player(Builder builder) {
        username = builder.username;
        password = builder.password;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String username;
        private String password;

        private Builder() {
        }

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withPassword(String val) {
            password = val;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }

    @Override
    public String toString() {
        return "Player : " + username + " password: " + password;
    }
}
