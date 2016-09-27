package com.loffa.ofstest.dao;

import com.loffa.ofstest.core.Player;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lloughlin on 26/09/2016.
 */
public class PlayerMapper implements ResultSetMapper<Player> {


    @Override
    public Player map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Player(r.getString("username"), r.getString("password"));
    }
}
