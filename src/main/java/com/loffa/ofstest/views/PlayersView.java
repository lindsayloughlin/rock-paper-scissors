package com.loffa.ofstest.views;


import com.loffa.ofstest.core.Player;
import io.dropwizard.views.View;
import java.util.Collections;
import java.util.List;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class PlayersView extends View {

    final private List<Player> players;

    public PlayersView(List<Player> players) {
        super("playersview.ftl");
        this.players = players != null ? players : Collections.emptyList() ;
    }

    public List<Player> getPlayers() {
        return players;
    }

}
