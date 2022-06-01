package com._0myun.minecraft.peacewarrior.dao;

import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PlayerDataDao extends BaseDaoImpl<PlayerData, Integer> {
    public PlayerDataDao(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public PlayerDataDao(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PlayerDataDao(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public PlayerData queryForUsername(String name) throws SQLException {
        return queryForFirst(queryBuilder().where().eq("player", name).prepare());
    }

    public PlayerData queryForUUID(UUID uuid) throws SQLException {
        return queryForFirst(queryBuilder().where().eq("uuid", uuid.toString()).prepare());
    }

    public List<PlayerData> queryForMap(String map) {
        try {
            return query(queryBuilder().where().eq("map", map).prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<PlayerData> queryForMap(String map, PlayerData.Stat... stat) {
        try {
            return query(queryBuilder().where().eq("map", map).and().in("stat", stat).prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PlayerData> queryForStat(PlayerData.Stat... stat) {
        try {
            return query(queryBuilder().where().in("stat", stat).prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
