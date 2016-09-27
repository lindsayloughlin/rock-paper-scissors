package com.loffa.ofstest.dao;

import com.loffa.ofstest.core.Player;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by lloughlin on 22/09/2016.
 */


@RegisterMapper(PlayerMapper.class)
public interface PlayerDao {

    @SqlQuery("Select * from player")
    List<Player> getAll();

    @SqlQuery("select * from player where usersname = :username")
    Player findByUsername(@Bind("username") String username);

    @SqlUpdate("delete * from player where username = :username")
    int deleteByUsername(@Bind("username") String username);

    @SqlUpdate("update player set username = :username, password = :password")
    int update(@BindBean Player player);

    @SqlUpdate("insert into player (username, password) values (:username, :password)")
    int insert(@BindBean Player player);

}
