package com.loffa.ofstest.api;

import com.loffa.ofstest.core.GameContent;
import com.loffa.ofstest.core.MoveMade;
import com.loffa.ofstest.core.enums.GameResultType;
import com.loffa.ofstest.core.enums.MoveType;

import java.util.ArrayList;
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
                .withResultType(doesFirstBeatSecond(moveMade.moveType,randomMove))
                .build();
        gameResults.add(gameContent);
        PersistenceService.getInstance().saveToDefaultFile();
        return gameContent;
    }

    public static synchronized GameResultService createInstanceFrom(List<GameContent> gameContent) {
        instance = new GameResultService(gameContent);
        return instance;
    }

    private static MoveType generateRandomMove() {

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
