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


    final List<Player> validPlayers;
    final PersistenceService persistenceService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    public PlayerService(PersistenceService persistenceService) {
        this.validPlayers = persistenceService.getPlayers();
        this.persistenceService = persistenceService;
        LOGGER.debug("Created player service with players: " + validPlayers);
    }



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
        // TODO: give some reasons why... possibly a list of errors?
        if (Strings.isNullOrEmpty(player.username)) {
            return false;
        }

        Map<String, Player> playersMap = getPlayersMap();
        if (playersMap.containsKey(player.username.toLowerCase())) {
            // Done for testing reasons.
            return validatePlayerPassword(player.username, player.password);
        }

        validPlayers.add(player);
        persistenceService.saveToDefaultFile();
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
