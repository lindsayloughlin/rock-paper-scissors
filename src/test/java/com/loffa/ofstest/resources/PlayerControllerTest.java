package com.loffa.ofstest.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loffa.ofstest.api.PersistenceService;
import com.loffa.ofstest.api.PlayerService;
import com.loffa.ofstest.core.Player;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by lloughlin on 20/09/2016.
 */
public class PlayerControllerTest {

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new PlayerController(new PlayerService(new PersistenceService(new ObjectMapper()))))
            .build();


    @Test
    public void shouldCreatePlayer() {

        final Response response = resources.client().target("/player")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(Player.newBuilder()
                        .withUsername("test-username")
                        .withPassword("test-password")
                        .build(), MediaType.APPLICATION_JSON));
        Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
    }


}
