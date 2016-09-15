package com.loffa.ofstest.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.loffa.ofstest.api.PersistenceService;
import com.loffa.ofstest.api.PlayerService;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by lloughlin on 16/09/2016.
 */
public class PersistentDataTest {

    @Test
    public void canDeserializeGameData() throws URISyntaxException, IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());
        URL url = Resources.getResource("./fixtures/sample_game_data.json");
        Assert.assertNotNull(url);
        byte[] encoded = Files.toByteArray(new File(url.toURI()));
        String gameData = new String(encoded, StandardCharsets.UTF_8);
        PersistenceData persistenceData = objectMapper.readValue(gameData, PersistenceData.class);
        Assert.assertEquals(3, persistenceData.players.size());
        Assert.assertEquals(64, persistenceData.gamesPlayed.size());

    }


}
