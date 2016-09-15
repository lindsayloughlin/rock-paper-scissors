package com.loffa.ofstest.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class PersistenceData {


    @JsonProperty("players")
    public final List<Player> players;

    @JsonProperty("games")
    public final List<GameContent> gamesPlayed;

    @JsonCreator
    public PersistenceData(@JsonProperty("players") List<Player> users,
                           @JsonProperty("games") List<GameContent> gamesPlayed) {

        this.players = users != null ? users : new ArrayList<>();
        this.gamesPlayed = gamesPlayed != null ? gamesPlayed : new ArrayList<>();
    }


    private PersistenceData(Builder builder) {
        players = builder.players;
        gamesPlayed = builder.gamesPlayed;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private List<Player> players;
        private List<GameContent> gamesPlayed;

        private Builder() {
        }

        public Builder withPlayers(List<Player> val) {
            players = val;
            return this;
        }

        public Builder withGamesPlayed(List<GameContent> val) {
            gamesPlayed = val;
            return this;
        }

        public PersistenceData build() {
            return new PersistenceData(this);
        }
    }
}
