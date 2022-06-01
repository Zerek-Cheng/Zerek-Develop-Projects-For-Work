package com._0myun.minecraft.peacewarrior.dao;

import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

public class BattleDao extends BaseDaoImpl<Battle, Integer> {
    public BattleDao(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public BattleDao(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public BattleDao(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }


    public Battle queryForMap(String map, Battle.Stat... stats) throws SQLException {
        return queryForFirst(queryBuilder().orderBy("id", false).where().eq("map", map).and().in("stat", stats).prepare());
    }

    public Battle queryForMap(String map) throws SQLException {
        return queryForFirst(queryBuilder().orderBy("id", false).where().eq("map", map).prepare());
    }

    public List<Battle> queryForStat(Battle.Stat... stats) throws SQLException {
        return query(queryBuilder().orderBy("id", false).where().in("stat", stats).prepare());
    }
}
