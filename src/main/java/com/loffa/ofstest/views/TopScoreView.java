package com.loffa.ofstest.views;


import com.loffa.ofstest.api.GamePlayService;
import com.loffa.ofstest.api.GameResultService;
import com.loffa.ofstest.core.HighScore;
import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

/**
 * Created by lloughlin on 11/09/2016.
 */

public class TopScoreView extends View {

    public List<HighScore> getHighScoreList(int numPlaces) {
        return GameResultService.getInstance().getRecentHighScoreList(numPlaces);
    }

    public TopScoreView()
    {
        super("topscoreview.ftl");
    }
}
