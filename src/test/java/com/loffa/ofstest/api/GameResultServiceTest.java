package com.loffa.ofstest.api;

import com.google.common.collect.ImmutableList;
import com.loffa.ofstest.core.GameContent;
import com.loffa.ofstest.core.HighScore;
import com.loffa.ofstest.core.MoveMade;
import com.loffa.ofstest.core.enums.GameResultType;
import com.loffa.ofstest.core.enums.MoveType;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class GameResultServiceTest {

    @After
    public void removeData() {
        GameResultService.getInstance().removeData();
        ;
    }

    @Test
    public void shouldBeatPaperWithScissors() {
        Assert.assertEquals(GameResultType.Win, GameResultService.doesFirstBeatSecond(MoveType.Scissor, MoveType.Paper));
    }

    @Test
    public void shouldBeatScissorsWithRock() {
        Assert.assertEquals(GameResultType.Win, GameResultService.doesFirstBeatSecond(MoveType.Rock, MoveType.Scissor));
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
        GameResultService resultService = GameResultService.getInstance();
        List<GameContent> gameResults = resultService.getGameResults();
        gameResults.add(
                GameContent.newBuilder()
                        .withPlayerTwoMove(MoveMade.newBuilder()
                                        .withUsername("old-player")
                                        .build()
                        ).withGameType(lastYear)
                .build());
        List<GameContent> gamesPlayedUpHoursAgo = resultService.getGamesPlayedUpHoursAgo(1);
        Assert.assertTrue(gamesPlayedUpHoursAgo.isEmpty());

    }

    @Test
    public void shouldAllowHighScoresUpToAnHourAgo() {
        GameResultService resultService = GameResultService.getInstance();
        List<GameContent> gameResults = resultService.getGameResults();
        gameResults.add(
                GameContent.newBuilder()
                        .withGameType((new DateTime()).minusMinutes(59))
                        .build()
        );

        Assert.assertEquals(1, gameResults.size());
        List<GameContent> gamesPlayedUpHoursAgo = resultService.getGamesPlayedUpHoursAgo(1);
        Assert.assertEquals(1, gamesPlayedUpHoursAgo.size());
    }

    @Test
    public void shouldGetValidResultAgainstRandom() {
        GameResultService gameResultService = GameResultService.getInstance();
        GameContent gameResult = gameResultService
                .performRandomGameForUser(
                        MoveMade.newBuilder()
                                .withMoveType(MoveType.Paper)
                                .withUsername("johhny")
                                .build());
        Assert.assertNotNull(gameResult);
        Assert.assertEquals(1, gameResultService.getGameResults().size());
    }

    @Test
    public void shouldFilterResults() {

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

    @Test
    public void shouldSortNumberOfResultsCorrectly() {

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
                                            .withMoveType(MoveType.Scissor)
                                            .build()
                            ).build()
            );
        }
        return list;
    }

}
