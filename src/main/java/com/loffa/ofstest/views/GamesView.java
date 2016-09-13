package com.loffa.ofstest.views;

import com.loffa.ofstest.api.GameResultService;
import com.loffa.ofstest.core.GameContent;
import io.dropwizard.views.View;

import java.util.List;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class GamesView extends View {

    public List<GameContent> getGamesContent() {
        return gamesContent;
    }

    private List<GameContent> gamesContent;

    public GamesView() {
        super("gamescontent.ftl");
        gamesContent = GameResultService.getInstance().getGameResults();
    }
}
