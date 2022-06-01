package com._0myun.minecraft.asynpokemonslot.ormlite.data;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBManager {
    public static JdbcConnectionSource conn;
    public static PokemonDataDao pokemonDataDao;

    public static void initDao() {
        try {
            pokemonDataDao = new PokemonDataDao(conn, PokemonData.class);
            if (!pokemonDataDao.isTableExists()) {
                TableUtils.createTable(pokemonDataDao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
