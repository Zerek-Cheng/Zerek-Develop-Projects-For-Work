package com._0myun.minecraft.pixelmonknockout.dao;

import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerGameData;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PlayerGameDataDao extends BaseDaoImpl<PlayerGameData, Integer> {
    public PlayerGameDataDao(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public PlayerGameDataDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, PlayerGameData.class);
    }

    public PlayerGameDataDao(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }


    public List<PlayerGameData> queryForUUID(UUID uuid) throws SQLException {
        return query(queryBuilder().where().eq("uuid", uuid).prepare());
    }

    public PlayerGameData queryForUUID(UUID uuid, String game) throws SQLException {
        return queryForFirst(queryBuilder().where().eq("uuid", uuid).and().eq("game", game).prepare());
    }

    public List<PlayerGameData> queryForGame(String map) {
        try {
            return query(queryBuilder().where().eq("game", map).prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
