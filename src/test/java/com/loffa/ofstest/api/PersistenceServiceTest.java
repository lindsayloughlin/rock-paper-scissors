package com.loffa.ofstest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.loffa.ofstest.core.PersistenceData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class PersistenceServiceTest {

    PersistenceService instance;
    @Before
    public void setupObjectMapper() {
        instance = new PersistenceService(new ObjectMapper().registerModule(new JodaModule()));
    }

    @Test
    public void shouldLoadGameData() throws IOException {

        String emptyGameData = "{}";
        PersistenceData persistenceData = instance.loadGameDataFromString(emptyGameData);
        Assert.assertNotNull(persistenceData);
        Assert.assertNotNull(persistenceData.gamesPlayed);
        Assert.assertNotNull(persistenceData.players);
    }
}
