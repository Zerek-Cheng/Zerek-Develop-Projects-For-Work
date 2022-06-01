package com._0myun.minecraft.peacewarrior.dao;

import com._0myun.minecraft.peacewarrior.dao.data.PlayerInv;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PlayerInvDao extends BaseDaoImpl<PlayerInv, Integer> {
    public PlayerInvDao(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public PlayerInvDao(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PlayerInvDao(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List<PlayerInv> queryForUUID(UUID uuid) throws SQLException {
        return query(queryBuilder().where().eq("uuid", uuid.toString()).prepare());
    }

}
