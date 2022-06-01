package com._0myun.minecraft.asynpokemonslot.ormlite.data;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.UUID;

public class PokemonDataDao extends BaseDaoImpl<PokemonData, Integer> {
    public PokemonDataDao(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public PokemonDataDao(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PokemonDataDao(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }


    public PokemonData queryForUUID(UUID uuid) throws SQLException {
        return queryForFirst(queryBuilder().where().eq("uuid", uuid.toString()).prepare());
    }
}
