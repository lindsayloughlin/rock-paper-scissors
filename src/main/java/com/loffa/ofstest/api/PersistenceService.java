package com.loffa.ofstest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.loffa.ofstest.core.GameContent;
import com.loffa.ofstest.core.PersistenceData;
import com.loffa.ofstest.core.Player;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class PersistenceService {

    final static String GAME_DATA = "game-data.json";
    private PersistenceData persistenceData;
    
    private ObjectMapper jacksonMapper;


    public PersistenceService(ObjectMapper objectMapper) {
        this.jacksonMapper = objectMapper;
        persistenceData = PersistenceData.newBuilder()
                .withGamesPlayed(new ArrayList<>())
                .withPlayers(new ArrayList<>())
                .build();
    }

    List<Player> getPlayers() {
        return persistenceData.players;
    }
    List<GameContent> getGames() {
        return persistenceData.gamesPlayed;
    }

    public void saveToDefaultFile() {
        try {
            saveDataToFile(GAME_DATA);
        }
        catch (IOException exception) {
            System.out.println("unable to save to " + GAME_DATA);
        }
    }

    public void loadDataFromJson() {
        loadGameData(GAME_DATA);
    }

    void saveDataToFile(String fileName) throws IOException {
        String outputStr = jacksonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(persistenceData);
        Files.write(outputStr, new File(fileName), StandardCharsets.UTF_8);
    }

    private void loadGameData(String fileName) {

        if (new File(fileName).exists()) {
            try {
                byte[] encoded = Files.toByteArray(new File(fileName));
                String gameData = new String(encoded, StandardCharsets.UTF_8);
                persistenceData = loadGameDataFromString(gameData);
            } catch (IOException e) {
                // Probably doesn't exist.
                //TODO: log out the file location.
                System.out.println(e.getMessage());
                throw new RuntimeException("Unable to load gamedata file " + fileName);
            }
        }
        if (persistenceData == null) {
            persistenceData = new PersistenceData(new ArrayList<>(), new ArrayList<>());
        }
    }

     PersistenceData  loadGameDataFromString(String gameDataAsStr) throws IOException {
         return jacksonMapper.readValue(gameDataAsStr, PersistenceData.class);
    }

}
