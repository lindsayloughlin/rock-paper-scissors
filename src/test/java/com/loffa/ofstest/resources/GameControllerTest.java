package com.loffa.ofstest.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loffa.ofstest.api.GameResultService;
import com.loffa.ofstest.api.PersistenceService;
import com.loffa.ofstest.api.PlayerService;
import com.loffa.ofstest.core.GameContent;
import com.loffa.ofstest.core.MoveMade;
import com.loffa.ofstest.core.enums.MoveType;
import com.loffa.ofstest.dao.GameDao;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by lloughlin on 20/09/2016.
 */

public class GameControllerTest {

    public final static PersistenceService persistenceService = new PersistenceService(new ObjectMapper());
    public final static PlayerService playerService = mock(PlayerService.class);
    public GameDao gameDao = mock(GameDao.class);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new GameController(new GameResultService(gameDao),
                            playerService)
            ).build();

    @Before
    public void allowAnyBodyIn() {

        when(playerService.validatePlayerPassword(anyString(), anyString())).thenReturn(Boolean.TRUE);
        Assert.assertTrue(playerService.validatePlayerPassword("hello", "world"));
    }

    @Test
    public void canGetHighScoreWhenEmpty() {
        Response response = resources.client().target("/game/highscore").request().get();
        Assert.assertEquals(HttpStatus.OK_200,response.getStatus());
    }

    @Test
    public void canPlayARandomGame() {

        Response response = resources.client().target("/game/playrandom").request().post(
                Entity.entity(
                        MoveMade.newBuilder()
                                .withUsername("user1")
                                .withMoveType(MoveType.Paper)
                                .build()
                        , MediaType.APPLICATION_JSON
                ));

        Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
    }

    @Test
    public void canPlayPatternMatchGame() throws IOException {

        Response response = resources.client().target("/game/playpattern").request().post(
                Entity.entity(
                        MoveMade.newBuilder()
                                .withUsername("user1")
                                .withMoveType(MoveType.Paper)
                                .build()
                        , MediaType.APPLICATION_JSON
                ));

        Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
        Assert.assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
        String responseAsString = response.readEntity(String.class);
        Assert.assertNotNull(responseAsString);
        GameContent content = resources.getObjectMapper().readValue(responseAsString, GameContent.class);
        Assert.assertEquals(MoveType.Paper, content.playerOneMove.moveType);


    }

}
