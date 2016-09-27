package com.loffa.ofstest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.loffa.ofstest.core.GameContent;
import com.loffa.ofstest.core.HighScore;
import com.loffa.ofstest.core.MoveMade;
import com.loffa.ofstest.core.enums.GameResultType;
import com.loffa.ofstest.core.enums.MoveType;
import com.loffa.ofstest.dao.GameDao;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class GameResultServiceTest {

    GameResultService resultService;
    GameDao gameDao = mock(GameDao.class);

    @Before()
    public void setupGameService()
    {
        resultService = new GameResultService(gameDao);
    }

    @Test
    public void shouldBeatPaperWithScissors() {
        Assert.assertEquals(GameResultType.Win, GameResultService.doesFirstBeatSecond(MoveType.Scissors, MoveType.Paper));
    }

    @Test
    public void shouldBeatScissorsWithRock() {
        Assert.assertEquals(GameResultType.Win, GameResultService.doesFirstBeatSecond(MoveType.Rock, MoveType.Scissors));
    }

    @Test
    public void shouldBeatRockWithPaper() {
        Assert.assertEquals(GameResultType.Win, GameResultService.doesFirstBeatSecond(MoveType.Paper, MoveType.Rock));
    }

    @Test
    public void shouldDrawIfSameTypes() {
        for (MoveType moveType : MoveType.values()) {
            Assert.assertEquals(GameResultType.Draw, GameResultService.doesFirstBeatSecond(moveType, moveType));
        }
    }

    @Test
    public void shouldRestrictHighScoresToAnHourOld() {

        DateTime lastYear = (new DateTime()).minusYears(1);

        List<GameContent> gameResults = resultService.getGameResults();
        gameResults.add(
                GameContent.newBuilder()
                        .withPlayerTwoMove(MoveMade.newBuilder()
                                        .withUsername("old-player")
                                        .build()
                        ).withGameTime(lastYear)
                        .build());
        List<GameContent> gamesPlayedUpHoursAgo = resultService.getGamesPlayedUpHoursAgo(1);
        Assert.assertTrue(gamesPlayedUpHoursAgo.isEmpty());

    }

    @Test
    public void shouldAllowHighScoresUpToAnHourAgo() {

        List<GameContent> gameResults = resultService.getGameResults();
        gameResults.add(
                GameContent.newBuilder()
                        .withGameTime((new DateTime()).minusMinutes(59))
                        .build()
        );

        Assert.assertEquals(1, gameResults.size());
        List<GameContent> gamesPlayedUpHoursAgo = resultService.getGamesPlayedUpHoursAgo(1);
        Assert.assertEquals(1, gamesPlayedUpHoursAgo.size());
    }

    @Test
    public void shouldGetValidResultAgainstRandom() {

        GameContent gameResult = resultService
                .performRandomGameForUser(
                        MoveMade.newBuilder()
                                .withMoveType(MoveType.Paper)
                                .withUsername("johhny")
                                .build());
        Assert.assertNotNull(gameResult);
        Assert.assertEquals(1, resultService.getGameResults().size());
    }

    @Test
    public void shouldNotGivePredictionForSmallResultSet() {
        Optional<MoveType> predictedMove = GameResultService.getPredictedMove(ImmutableList.of(MoveType.Paper));
        Assert.assertFalse(predictedMove.isPresent());
    }

    @Test
    public void shouldGivePatternMatchedAnswerAllSame() {

        Optional<MoveType> predictedMove = GameResultService.getPredictedMove(ImmutableList.of(
                MoveType.Paper, MoveType.Paper, MoveType.Paper, MoveType.Paper
        ));
        Assert.assertTrue(predictedMove.isPresent());
        Assert.assertEquals(MoveType.Paper, predictedMove.get());
    }

    @Test
    public void shouldNotFindPattern() {

        List<MoveType> movesMade = new ArrayList<MoveType>();
        for (int i = 0; i < 10; i++ ) {
            movesMade.add(MoveType.Rock);
            movesMade.add(MoveType.Rock);
            movesMade.add(MoveType.Scissors);
        }
        //movesMade.add(MoveType.Paper);
        movesMade.add(MoveType.Paper);
        movesMade.add(MoveType.Paper);
        movesMade.add(MoveType.Paper);

        Optional<MoveType> predictedMove = GameResultService.getPredictedMove(movesMade);
        Assert.assertFalse(predictedMove.isPresent());
    }


    @Test
    public void shouldShowHighScoreShouldNotIncludeDraw() {

        List<GameContent> allResults = new ArrayList<GameContent>();
        allResults.addAll(fixtureWinList(10, "top-player"));
        allResults.addAll(fixtureWinList(5, "less-good"));
        List<HighScore> highScores = GameResultService.generateHighScoreFor(allResults, 10);
        Assert.assertEquals(2, highScores.size());
        HighScore topScore = highScores.get(0);
        Assert.assertEquals("top-player", topScore.getUsername());
        HighScore lessScore = highScores.get(1);
        Assert.assertEquals("less-good", lessScore.getUsername());
    }


    private List<GameContent> fixtureWinList(int number, String username) {
        List<GameContent> list = new ArrayList<>();
        for (int i = 0; i < number; ++i) {
            list.add(
                    GameContent.newBuilder()
                            .withPlayerOneMove(MoveMade.newBuilder()
                                            .withUsername(username)
                                            .withMoveType(MoveType.Rock)

                                            .build()
                            )
                            .withResultType(GameResultType.Win)
                            .withPlayerTwoMove(
                                    MoveMade.newBuilder()
                                            .withUsername("not-winner")
                                            .withMoveType(MoveType.Scissors)
                                            .build()
                            ).build()
            );
        }
        return list;
    }

}
