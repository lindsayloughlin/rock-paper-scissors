package com.loffa.ofstest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loffa.ofstest.core.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class PlayerServiceTest {

    PlayerService playerService;

    @Before
    public void setupPlayerService() {
        playerService = new PlayerService(new PersistenceService(new ObjectMapper()));
    }

    @Test
    public void shouldNotAddUserWithEmptyUsername() {
        Assert.assertFalse(playerService.addPlayer(Player.newBuilder()
                        .withUsername("")
                        .build())
        );
    }

    @Test
    public void shouldAddUsersWithValidUsernamePassword() {
        Assert.assertTrue(playerService.addPlayer(
                        Player.newBuilder()
                                .withUsername("test-user")
                                .withPassword("test-password")
                                .build())
        );

        List<Player> players = playerService.getValidPlayers();
        Assert.assertEquals(1, players.size());
        Player player = players.get(0);
        Assert.assertEquals("test-user", player.username);
        Assert.assertEquals("test-password", player.password);
    }

    @Test
    public void shouldNotAddUserTwice() {
        Assert.assertTrue(playerService.addPlayer(
                Player.newBuilder()
                        .withUsername("test-user")
                        .withPassword("test-password")
                        .build()));

        Assert.assertFalse(playerService.addPlayer(
                Player.newBuilder()
                .withUsername("test-user")
                .withPassword("even with different password")
                .build()));
    }

    @Test
    public void shouldValidateUserPasswordWithCorrect() {
        playerService.addPlayer(
                Player.newBuilder()
                .withUsername("test-user")
                .withPassword("test-password")
                .build());
        Assert.assertTrue(playerService.validatePlayerPassword("test-user", "test-password"));
    }

    @Test
    public void shouldNotValidateUserPasswordWhenIncorrect() {
        playerService.addPlayer(
                Player.newBuilder()
                .withUsername("test-user")
                .withPassword("test-password")
                .build());
        Assert.assertFalse(playerService.validatePlayerPassword("test-user", "not-right-password"));

    }
}
