package com.loffa.ofstest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loffa.ofstest.api.GameResultService;
import com.loffa.ofstest.api.PlayerService;
import com.loffa.ofstest.core.GameContent;
import com.loffa.ofstest.core.HighScore;
import com.loffa.ofstest.core.MoveMade;
import com.loffa.ofstest.core.enums.MoveType;
import com.loffa.ofstest.views.ArenaView;

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

    public static class AuthMove {

        @JsonProperty(value = "username",required = true)
        public final String username;

        @JsonProperty(value = "password", required = true)
        public final String password;

        @JsonProperty(value = "move", required = true)
        public final MoveType moveType;

        public AuthMove(@JsonProperty("username") String username,
                        @JsonProperty("password") String password,
                        @JsonProperty("move") MoveType moveType) {
            this.username = username;
            this.password = password;
            this.moveType = moveType;
        }
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
    @Produces(MediaType.APPLICATION_JSON)
    public GameContent playAgainstRandomComputer(AuthMove moveMade) throws Exception {

        getPlayerCredential(moveMade);
        GameContent gameContent = resultService.performRandomGameForUser(
                MoveMade.newBuilder()
                        .withUsername(moveMade.username)
                        .withMoveType(moveMade.moveType)
                        .build());
        return gameContent;
    }

    private void getPlayerCredential(AuthMove moveMade) throws Exception {
        if (!playerService.validatePlayerPassword(moveMade.username, moveMade.password)) {
            throw new Exception("can't find player " + moveMade.username);
        }
    }

    @POST
    @Path("/playpattern")
    @Produces(MediaType.APPLICATION_JSON)
    public GameContent playAgainstPatternMatchingComputer(AuthMove moveMade) throws Exception {
        // Should be done in the filter layer.
        getPlayerCredential(moveMade);
        GameContent gameContent = resultService.performPatternMatchedGame(
                MoveMade.newBuilder()
                        .withUsername(moveMade.username)
                        .withMoveType(moveMade.moveType)
                        .build());
        return gameContent;
    }
}
