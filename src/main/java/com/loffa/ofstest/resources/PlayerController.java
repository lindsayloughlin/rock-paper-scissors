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

    final PlayerService service;
    public PlayerController(PlayerService service) {
        this.service =service;
    }

    @POST
    public Response addPlayer(Player player) {
        if (service.addPlayer(player)) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }

    @GET
    @Path("{username}/info")
    public Player getPlayerInfo(@PathParam("username") String username) throws Exception {

        if (service.getPlayersMap().containsKey(username)) {
            return service.getPlayersMap().get(username);
        }
        throw new Exception("Unable to find user " + username);
    }

    @GET
    @Path("all")
    public PlayersView getPlayerView(@PathParam("username") String username) throws Exception {
        return new PlayersView(service.getValidPlayers());
    }
}
