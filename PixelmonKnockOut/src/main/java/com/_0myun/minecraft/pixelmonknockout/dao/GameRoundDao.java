package com._0myun.minecraft.pixelmonknockout.dao;

import com._0myun.minecraft.pixelmonknockout.dao.data.GameRound;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

public class GameRoundDao extends BaseDaoImpl<GameRound, Integer> {
    public GameRoundDao(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public GameRoundDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, GameRound.class);
    }

    public GameRoundDao(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }


    public List<GameRound> queryForGame(String game, GameRound.Stat... stats) throws SQLException {
        return query(queryBuilder().orderBy("id", false).where().eq("game", game).and().in("stat", stats).prepare());
    }

    public GameRound queryForGame(String game) throws SQLException {
        return queryForFirst(queryBuilder().orderBy("id", false).where().eq("game", game).prepare());
    }

    public List<GameRound> queryForStat(GameRound.Stat... stats) throws SQLException {
        return query(queryBuilder().orderBy("id", false).where().in("stat", stats).prepare());
    }
}
