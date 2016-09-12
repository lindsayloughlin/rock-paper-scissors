package com.loffa.ofstest.views;


import com.loffa.ofstest.core.Player;
import io.dropwizard.views.View;

import javax.ws.rs.GET;
import java.nio.charset.Charset;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class PlayersView extends View {


    final private Player player;

    public PlayersView(Player player) {
        super("playerview.ftl");
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
