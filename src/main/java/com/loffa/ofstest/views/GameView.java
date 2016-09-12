package com.loffa.ofstest.views;

import com.loffa.ofstest.core.GameContent;
import io.dropwizard.views.View;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class GameView extends View {

    public final GameContent gameContent;

    GameView(GameContent gameContent) {
        super("gamecontent.ftl");
        this.gameContent = gameContent;
    }
}
