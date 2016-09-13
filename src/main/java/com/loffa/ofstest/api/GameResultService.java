package com.loffa.ofstest.api;

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
import java.util.Random;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class GameResultService {

    private List<GameContent> gameResults = new ArrayList<>();

    private static GameResultService instance;

    public static synchronized GameResultService getInstance() {
        if (instance == null) {
            instance = new GameResultService(new ArrayList<>());
        }
        return instance;
    }

    public  void removeData() {
        getGameResults().clear();
    }

    public List<GameContent> getGameResults() {
        return gameResults;
    }

    public GameResultService(List<GameContent> gameResults) {
        this.gameResults = gameResults;
    }

    public GameContent performRandomGameForUser(MoveMade moveMade) {
        MoveType randomMove = generateRandomMove();
        MoveMade cpuMove = MoveMade.newBuilder()
                .withMoveType(randomMove)
                .withUsername("cpu")
                .build();
        GameContent gameContent = GameContent
                .newBuilder()
                .withPlayerOneMove(moveMade)
                .withPlayerTwoMove(cpuMove)
                .withResultType(doesFirstBeatSecond(moveMade.moveType, randomMove))
                .withGameType(DateTime.now())
                .build();
        gameResults.add(gameContent);
        PersistenceService.getInstance().saveToDefaultFile();
        return gameContent;
    }

    public static synchronized GameResultService createInstanceFrom(List<GameContent> gameContent) {
        instance = new GameResultService(gameContent);
        return instance;
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
//        List<GameContent> recentHighScore = Collections2.filter(gameResults, new Predicate<GameContent>() {
//            @Override
//            public boolean apply(GameContent gameContent) {
//
//                if (gameContent.gameTime == null) {
//                    return false;
//                }
//                return cutOffDate.isBefore(gameContent.gameTime.getMillis());
//            }
//        });

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
        List<HighScore> unsortedScoreList = new ArrayList<>();
        for (String player : winnerToGame.keySet()) {
            unsortedScoreList.add(HighScore.newBuilder()
                    .withUsername(player)
                    .withWin(winnerToGame.get(player).size())
                    .build());
        }

        Collections.sort(unsortedScoreList, new Comparator<HighScore>() {
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
        return unsortedScoreList;
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
        if (first == MoveType.Paper) {

            if (second == MoveType.Rock) {
                return GameResultType.Win;
            } else {
                return GameResultType.Loss;
            }
        } else if (first == MoveType.Scissor) {
            if (second == MoveType.Paper) {
                return GameResultType.Win;
            } else {
                return GameResultType.Loss;
            }
        } else {
            // ROCK path.
            if (second == MoveType.Scissor) {
                return GameResultType.Win;
            } else {
                return GameResultType.Loss;
            }
        }
        //throw new Exception("Unknown MoveType type for " + first);
    }


}
