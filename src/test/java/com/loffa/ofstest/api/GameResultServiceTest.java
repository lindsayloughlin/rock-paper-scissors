package com.loffa.ofstest.api;

import com.loffa.ofstest.core.GameContent;
import com.loffa.ofstest.core.MoveMade;
import com.loffa.ofstest.core.enums.GameResultType;
import com.loffa.ofstest.core.enums.MoveType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lloughlin on 11/09/2016.
 */
public class GameResultServiceTest {

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
            Assert.assertEquals(GameResultType.Draw, GameResultService.doesFirstBeatSecond(moveType,moveType));
        }
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
}
