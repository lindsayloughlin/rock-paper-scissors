package com.loffa.ofstest.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loffa.ofstest.api.PersistenceService;
import com.loffa.ofstest.api.PlayerService;
import com.loffa.ofstest.core.Player;
import com.loffa.ofstest.dao.PlayerDao;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by lloughlin on 20/09/2016.
 */
public class PlayerControllerTest {


    PlayerDao playerDao = mock(PlayerDao.class);

    @Rule
    public  final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new PlayerController(new PlayerService(playerDao)))
            .build();

    @Test
    public void shouldCreatePlayer() {

        when(playerDao.insert(any(Player.class))).thenReturn(1);
        final Response response = resources.client().target("/player")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(Player.newBuilder()
                        .withUsername("test-username")
                        .withPassword("test-password")
                        .build(), MediaType.APPLICATION_JSON));
        Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
    }


}
