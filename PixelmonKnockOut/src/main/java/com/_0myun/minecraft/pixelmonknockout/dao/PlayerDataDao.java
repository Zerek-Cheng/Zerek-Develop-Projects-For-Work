package com._0myun.minecraft.pixelmonknockout.dao;

import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerData;
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

    public PlayerDataDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, PlayerData.class);
    }

    public PlayerDataDao(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }


    public PlayerData queryForUUID(UUID uuid) throws SQLException {
        return queryForFirst(queryBuilder().where().eq("uuid", uuid).prepare());
    }

    public List<PlayerData> queryForGame(int game) {
        try {
            return query(queryBuilder().where().eq("game", game).prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<PlayerData> queryForGame(int game, PlayerData.Stat... stat) {
        try {
            return query(queryBuilder().where().eq("game", game).and().in("stat", stat).prepare());
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
