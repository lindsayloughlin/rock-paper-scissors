package com.loffa.ofstest.api;

import com.loffa.ofstest.core.PersistenceData;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class PersistenceServiceTest {

    @Test
    public void shouldLoadGameData() throws IOException {

        String emptyGameData = "{}";
        PersistenceData persistenceData = PersistenceService.loadGameDataFromString(emptyGameData);
        Assert.assertNotNull(persistenceData);
        Assert.assertNotNull(persistenceData.gamesPlayed);
        Assert.assertNotNull(persistenceData.players);

    }

    @Test
    public void shouldHandleFileNotAvailable() throws IOException {
        PersistenceService instance = PersistenceService.getInstance();
        instance.loadGameData("/hello-world/not-a-file");
        //Assert.assertEquals(0, instance..size());
    }

    @Test
    public void shouldBeAbleToWriteToFile() {
        String testRunFile = "/tmp/test-run.json";


    }
}
