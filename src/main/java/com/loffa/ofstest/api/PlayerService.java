package com.loffa.ofstest.api;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.loffa.ofstest.core.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class PlayerService {

    static PlayerService instance;

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    public static synchronized PlayerService getInstance() {
        if (instance == null) {
            instance = new PlayerService(new ArrayList<>());
        }
        return instance;
    }

    static void createFromPlayers(List<Player> players ) {

        LOGGER.debug("Created player service with players: " + players);
        instance = new PlayerService(players);

    }

    PlayerService(List<Player> players) {
        this.validPlayers = players;
    }

    private List<Player> validPlayers;

    public boolean playerExists(String username) {
        return getPlayersMap().containsKey(username);
    }

    public List<Player> getValidPlayers() {
        return validPlayers;
    }

    public Map<String, Player> getPlayersMap() {
        return Maps.uniqueIndex(validPlayers, new Function<Player, String>() {
            @Nullable
            @Override
            public String apply(Player player) {
                return player.username.toLowerCase();
            }
        });
    }

    public synchronized boolean  addPlayer(Player player) {

        // todo: give some reasons why... possibly a list of errors?
        if (Strings.isNullOrEmpty(player.username)) {
            return false;
        }

        Map<String, Player> playersMap = getPlayersMap();
        if (playersMap.containsKey(player.username.toLowerCase())) {
            // Done for testing reasons.
            return validatePlayerPassword(player.username, player.password);
        }

        validPlayers.add(player);
        PersistenceService.getInstance().saveToDefaultFile();
        return true;
    }

    public boolean validatePlayerPassword(String username, String password) {
        if (Strings.isNullOrEmpty(username)) {
            return false;
        }
        if (!getPlayersMap().containsKey(username.toLowerCase())) {
            return false;
        }

        Player player = getPlayersMap().get(username.toLowerCase());
        return player.password.equals(password);
    }
}
