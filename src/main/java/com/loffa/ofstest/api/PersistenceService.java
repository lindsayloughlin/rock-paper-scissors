package com.loffa.ofstest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.loffa.ofstest.core.PersistenceData;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class PersistenceService {

    PersistenceData persistenceData;

    PlayerService playerService;

    final static String GAME_DATA = "game-data.json";

    private static PersistenceService instance;

    public static synchronized PersistenceService getInstance() {
        if (instance == null) {
            instance = new PersistenceService();
        }
        return instance;
    }

    public void saveToDefaultFile() {
        try {
            saveDataToFile(GAME_DATA);
        }
        catch (IOException exception) {
            // chill
        }
    }

    public void loadFromDefaultFile() {
        loadGameData(GAME_DATA);
    }

    void saveDataToFile(String fileName) throws IOException {
        String outputStr = (new ObjectMapper()).writeValueAsString(persistenceData);
        Files.write(outputStr, new File(fileName), StandardCharsets.UTF_8);
    }

    void loadGameData(String fileName) {

        try {
            byte[] encoded = new byte[0];
            encoded = Files.toByteArray(new File(fileName));
            String gameData = new String(encoded, StandardCharsets.UTF_8);
            persistenceData = loadGameDataFromString(gameData);

        } catch (IOException e) {
            // Probably doesn't exist.
            //TODO: log out the file location.
        }
        if (persistenceData == null) {
            persistenceData = new PersistenceData(new ArrayList<>(), new ArrayList<>());
        }
        PlayerService.createFromPlayers(persistenceData.players);
        GameResultService.createInstanceFrom(persistenceData.gamesPlayed);
    }

    static PersistenceData  loadGameDataFromString(String gameDataAsStr) throws IOException {
         ObjectMapper mapper = new ObjectMapper();
         return mapper.readValue(gameDataAsStr, PersistenceData.class);
    }

}
