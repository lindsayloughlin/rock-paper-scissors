package com.loffa.ofstest.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class PersistenceData {

    @JsonCreator
    public PersistenceData(@JsonProperty("players") List<Player> users,
                           @JsonProperty("games") List<GameContent> gamesPlayed) {

        this.players = users != null ? users : new ArrayList<>();
        this.gamesPlayed = gamesPlayed != null ? gamesPlayed : new ArrayList<>();
    }

    @JsonProperty("players")
    public final List<Player> players;

    @JsonProperty("games")
    public final List<GameContent> gamesPlayed;
}
