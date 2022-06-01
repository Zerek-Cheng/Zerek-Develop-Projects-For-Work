package com._0myun.minecraft.pixelmonknockout.dao;

import com._0myun.minecraft.pixelmonknockout.dao.data.Games;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

public class GamesDao extends BaseDaoImpl<Games, Integer> {
    public GamesDao(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public GamesDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Games.class);
    }

    public GamesDao(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }


    public List<Games> queryForGame(String game, Games.Stat... stats) throws SQLException {
        return query(queryBuilder().orderBy("id", false).where().eq("game", game).and().in("stat", stats).prepare());
    }

    public Games queryForGame(String game) throws SQLException {
        return queryForFirst(queryBuilder().orderBy("id", false).where().eq("game", game).prepare());
    }

    public List<Games> queryForStat(Games.Stat... stats) throws SQLException {
        return query(queryBuilder().orderBy("id", false).where().in("stat", stats).prepare());
    }
}
