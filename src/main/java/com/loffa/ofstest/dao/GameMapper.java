package com.loffa.ofstest.dao;

import com.google.common.base.Strings;
import com.loffa.ofstest.core.GameContent;
import com.loffa.ofstest.core.MoveMade;
import com.loffa.ofstest.core.enums.MoveType;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by lloughlin on 27/09/2016.
 */
public class GameMapper implements ResultSetMapper<GameContent> {


    @Override
    public GameContent map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        int id = r.getInt("id");

        GameContent.Builder builder = GameContent.newBuilder()
                .withId(id);

        String playerOneUserName = r.getString("player_one_username");
        String playerOneMoveType = r.getString("player_one_move_type");
        if (!Strings.isNullOrEmpty(playerOneUserName) && !Strings.isNullOrEmpty(playerOneMoveType)) {
            builder.withPlayerOneMove(MoveMade.newBuilder()
                .withUsername(playerOneUserName)
                .withMoveType(MoveType.valueOf(playerOneMoveType))
                .build());
        }

        String playerTwoUsername = r.getString("player_one_username");
        String playerTwoMoveType = r.getString("player_two_move_type");

        // Covert to optional!!!
        if (!Strings.isNullOrEmpty(playerTwoUsername) && !Strings.isNullOrEmpty(playerTwoMoveType)) {
            builder.withPlayerTwoMove(MoveMade.newBuilder()
            .withUsername(playerTwoUsername)
            .withMoveType(MoveType.valueOf(playerTwoMoveType))
            .build());
        }

        Timestamp game_time = r.getTimestamp("game_time");
        if (game_time != null) {
            builder.withGameTime(new DateTime(game_time));
        }
        return builder.build();
    }
}
