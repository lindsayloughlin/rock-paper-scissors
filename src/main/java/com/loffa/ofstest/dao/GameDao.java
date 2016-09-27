package com.loffa.ofstest.dao;

import com.loffa.ofstest.core.GameContent;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by lloughlin on 26/09/2016.
 */

@RegisterMapper(GameMapper.class)
public interface GameDao {

    @SqlQuery("select * from gamecontent")
    List<GameContent> getAll();

    @SqlQuery("select * from gamecontent where ID = :id")
    GameContent findGameById(@Bind("id") int id);

    @SqlQuery("delete * from gamecontent where ID = :id")
    int deleteById(@Bind("id") int id);

//    @SqlUpdate("update gamecontent set player_one_move_type = :playerOneMove.moveType" +
//            " player_two_move_type = :playerTwoMove.moveType " +
//            " player_one_result = :resultType " +
//            "  where ID = :id")
//    int updateGameContent(@BindBean GameContent gameContent);

    //@SqlQuery("insert into gamecontent (player_one_move, player_two_move, player_one_username, player_two_username, player_one_result, game_time) " +
    @SqlUpdate("insert into gamecontent (game_time, player_one_move_type, player_two_move_type," +
            " player_one_username, player_two_username, player_one_result   ) " +
            "values( :gameTime, :playerOneMoveType, :playerTwoMoveType, :playerOneUsername,:playerTwoUsername, :playerOneResult)")
    int insert(@BindBean GameContent gameContent);

}
