package com.loffa.ofstest.api;


import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.loffa.ofstest.core.GameContent;
import com.loffa.ofstest.core.HighScore;
import com.loffa.ofstest.core.MoveMade;
import com.loffa.ofstest.core.enums.GameResultType;
import com.loffa.ofstest.core.enums.MoveType;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import java.util.Objects;
import java.util.Random;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class GameResultService {

    PersistenceService persistenceService;

    public GameResultService(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
        this.gameResults = persistenceService.getGames();
    }

    private List<GameContent> gameResults = new ArrayList<>();

    public void removeData() {
        getGameResults().clear();
    }

    public List<GameContent> getGameResults() {
        return gameResults;
    }

    public GameContent performPatternMatchedGame(MoveMade humanMove) {
        List<MoveType> allMoves = moveListForUser(humanMove.username);

        Optional<MoveType> predictedMove = getPredictedMove(allMoves);

        MoveType actualMoveType;
        if (predictedMove.isPresent()) {
            actualMoveType = predictedMove.get().getWinningMove();
        } else {
            actualMoveType = generateRandomMove();
        }
        GameContent gameContent = createGameFromMoves(humanMove, MoveMade.newBuilder()
                .withMoveType(actualMoveType)
                .withUsername("cpu-pattern")
                .build());
        return gameContent;
    }

    static Optional<MoveType> getPredictedMove(List<MoveType> allMoves) {

        int patternSize = 3;
        if (allMoves.size() < patternSize + 1) {
            return Optional.absent();
        }

        // No need to do pattern matching fallback to random.
        List<MoveType> recentMoves = allMoves.subList(allMoves.size() - patternSize, allMoves.size());
        // Grab the most lest recent to most recent list
        //for (int index = moveTypes.size() -1; index >= 0;  --index) {
        List<MoveType> matchingMoves = new ArrayList<MoveType>();
        Optional<MoveType> movePrediction = Optional.absent();
        for (int index = 0; index < (allMoves.size() - recentMoves.size()); ++index) {
            int matches = 0;
            for (int inner = 0; inner < recentMoves.size(); ++inner) {

                if (recentMoves.get(inner) == allMoves.get(index + inner)) {
                    ++matches;
                }
            }
            if (matches == recentMoves.size() && (index + 1) < allMoves.size()) {
                // Most likely move that the player will make
                matchingMoves.add(allMoves.get(index + 1));
            }
        }

        int moveCount = 0;
        Optional<MoveType> mostLikely = Optional.absent();
        for (MoveType moveCheck : MoveType.values()) {
            int frequency = Collections.frequency(matchingMoves, moveCheck);
            if (frequency > moveCount) {
                moveCount = frequency;
                mostLikely = Optional.of(moveCheck);
            }
        }
        return mostLikely;
    }

    private List<MoveType> moveListForUser(String username) {

        List<MoveType> movesForUser = new ArrayList<>();
        for (GameContent content : gameResults) {
            for (MoveMade move : content.getPlayersMoves())
                if (move.username.equals(username)) {
                    movesForUser.add(content.playerOneMove.moveType);
                }
        }
        return movesForUser;
    }

    public GameContent performRandomGameForUser(MoveMade humanMove) {
        MoveType randomMove = generateRandomMove();
        MoveMade cpuMove = MoveMade.newBuilder()
                .withMoveType(randomMove)
                .withUsername("cpu")
                .build();
        GameContent gameFromMoves = createGameFromMoves(humanMove, cpuMove);
        return gameFromMoves;
    }

    private GameContent createGameFromMoves(MoveMade playerOne, MoveMade playerTwo) {
        MoveType randomMove = generateRandomMove();
        GameContent gameContent = GameContent
                .newBuilder()
                .withPlayerOneMove(playerOne)
                .withPlayerTwoMove(playerTwo)
                .withResultType(doesFirstBeatSecond(playerOne.moveType, playerTwo.moveType))
                .withGameType(DateTime.now())
                .build();
        gameResults.add(gameContent);
        persistenceService.saveToDefaultFile();
        return gameContent;
    }


    public List<HighScore> getRecentHighScoreList(int maxPlaces) {
        List<GameContent> gamesPlayedUpHoursAgo = getGamesPlayedUpHoursAgo(1);
        List<HighScore> highScores = generateHighScoreFor(gamesPlayedUpHoursAgo, maxPlaces);
        return highScores;
    }

    public List<GameContent> getGamesPlayedUpHoursAgo(int hoursAgo) {
        DateTime cutOffDate = (DateTime.now()).minusHours(hoursAgo);

        List<GameContent> recentScore = new ArrayList<GameContent>();
        for (GameContent game : gameResults) {
            if (game.gameTime != null && game.gameTime.isAfter(cutOffDate.getMillis())) {
                recentScore.add(game);
            }
        }

        return recentScore;
    }

    public static List<HighScore> generateHighScoreFor(List<GameContent> gameContents, Integer numPlaces) {

        ArrayListMultimap<String, GameContent> winnerToGame = ArrayListMultimap.create();
        for (GameContent content : gameContents) {
            // Draws don't count
            if (content.resultType != GameResultType.Draw) {
                String winner = content.playerTwoMove.username;
                if (content.resultType == GameResultType.Win) {
                    winner = content.playerOneMove.username;
                }
                winnerToGame.put(winner, content);
            }
        }
        List<HighScore> highScoreList = new ArrayList<>();
        for (String player : winnerToGame.keySet()) {
            highScoreList.add(HighScore.newBuilder()
                    .withUsername(player)
                    .withWin(winnerToGame.get(player).size())
                    .build());
        }

        Collections.sort(highScoreList, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore o1, HighScore o2) {
                if (o1.getWins() == o2.getWins()) {
                    return 0;
                }
                if (o1.getWins() < o2.getWins()) {
                    return 1;
                }
                return -1;
            }
        });

        if (highScoreList.size() > numPlaces) {
            highScoreList = highScoreList.subList(0, numPlaces);
        }
        return highScoreList;
    }

    private MoveType generateRandomMove() {

        // Not really random
        Random random = new Random((new Date()).getTime());
        MoveType[] possibleMoves = MoveType.values();
        int moveType = random.nextInt(1000) % (possibleMoves.length);
        return possibleMoves[moveType];
    }

    public static GameResultType doesFirstBeatSecond(MoveType first, MoveType second) {

        if (first == second) {
            return GameResultType.Draw;
        }
        if (first.getWinningMove() == second) {
            return GameResultType.Loss;
        }

        return GameResultType.Win;
    }


}
