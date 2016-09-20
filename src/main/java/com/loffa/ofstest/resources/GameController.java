package com.loffa.ofstest.resources;

import com.loffa.ofstest.api.GameResultService;
import com.loffa.ofstest.api.PlayerService;
import com.loffa.ofstest.core.GameContent;
import com.loffa.ofstest.core.HighScore;
import com.loffa.ofstest.core.MoveMade;
import com.loffa.ofstest.views.ArenaView;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by lloughlin on 11/09/2016.
 */
@Path("/game/")
public class GameController {

    final GameResultService resultService;
    final PlayerService playerService;
    public GameController(GameResultService resultService,
                          PlayerService playerService) {
        this.resultService = resultService;
        this.playerService = playerService;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public ArenaView showBattleArena() {
        return new ArenaView();
    }

    @GET
    @Path("/highscore/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HighScore> highScoreList() {
        return resultService.getRecentHighScoreList(10);
    }

    @POST
    @Path("/playrandom/")
    @RolesAllowed("valid_player")
    @Produces(MediaType.APPLICATION_JSON)
    public GameContent playAgainstRandomComputer(MoveMade moveMade) throws Exception {
        GameContent gameContent = resultService.performRandomGameForUser(
                MoveMade.newBuilder()
                        .withUsername(moveMade.username)
                        .withMoveType(moveMade.moveType)
                        .build());
        return gameContent;
    }


    @POST
    @Path("/playpattern")
    @RolesAllowed("valid_player")
    @Produces(MediaType.APPLICATION_JSON)
    public GameContent playAgainstPatternMatchingComputer(MoveMade moveMade) throws Exception {
        // Should be done in the filter layer. <-- Woot now done
        GameContent gameContent = resultService.performPatternMatchedGame(
                MoveMade.newBuilder()
                        .withUsername(moveMade.username)
                        .withMoveType(moveMade.moveType)
                        .build());
        return gameContent;
    }

}
