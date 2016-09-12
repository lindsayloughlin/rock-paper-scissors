package com.loffa.ofstest.views;


import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by lloughlin on 11/09/2016.
 */

public class TopScoreView extends View {

    public TopScoreView()
    {
        super("topscoreview.ftl");
    }
}
