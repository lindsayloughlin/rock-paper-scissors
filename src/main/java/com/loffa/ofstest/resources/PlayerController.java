package com.loffa.ofstest.resources;

import com.loffa.ofstest.api.PlayerService;
import com.loffa.ofstest.core.Player;
import com.loffa.ofstest.views.PlayersView;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by lloughlin on 11/09/2016.
 */

@Path("/player/")
public class PlayerController {

    @POST
    public Response addPlayer(Player player) {

        PlayerService instance = PlayerService.getInstance();
        if (instance.addPlayer(player)) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }

    @GET
    @Path("{username}/info")
    public Player getPlayerInfo(@PathParam("username") String username) throws Exception {
        PlayerService instance = PlayerService.getInstance();
        if (instance.getPlayersMap().containsKey(username)) {
            return instance.getPlayersMap().get(username);
        }
        throw new Exception("Unable to find user " + username);
    }

    @GET
    @Path("all")
    public PlayersView getPlayerView(@PathParam("username") String username) throws Exception {
        PlayerService instance = PlayerService.getInstance();
        return new PlayersView(instance.getValidPlayers());
    }
}
