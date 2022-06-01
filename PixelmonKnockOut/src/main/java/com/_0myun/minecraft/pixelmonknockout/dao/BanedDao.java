package com._0myun.minecraft.pixelmonknockout.dao;

import com._0myun.minecraft.pixelmonknockout.dao.data.Baned;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class BanedDao extends BaseDaoImpl<Baned, Integer> {
    public BanedDao(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public BanedDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Baned.class);
    }

    public BanedDao(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }


    public List<Baned> queryForUUID(UUID uuid) throws SQLException {
        return query(queryBuilder().where().eq("uuid", uuid).prepare());
    }

    public Baned queryForUUID(UUID uuid, String game) throws SQLException {
        return queryForFirst(queryBuilder().where().eq("uuid", uuid).and().eq("game", game).prepare());
    }

    public List<Baned> queryForGame(String game) {
        try {
            return query(queryBuilder().where().eq("game", game).prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
