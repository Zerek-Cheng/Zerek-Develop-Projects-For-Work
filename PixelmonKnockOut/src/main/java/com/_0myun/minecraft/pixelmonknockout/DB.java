package com._0myun.minecraft.pixelmonknockout;

import com._0myun.minecraft.pixelmonknockout.dao.BanedDao;
import com._0myun.minecraft.pixelmonknockout.dao.GamesDao;
import com._0myun.minecraft.pixelmonknockout.dao.PlayerDataDao;
import com._0myun.minecraft.pixelmonknockout.dao.PlayerGameDataDao;
import com._0myun.minecraft.pixelmonknockout.dao.data.Baned;
import com._0myun.minecraft.pixelmonknockout.dao.data.Games;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerData;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerGameData;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DB {
    public static JdbcConnectionSource conn;
    public static GamesDao games;
    public static PlayerDataDao playerData;
    public static PlayerGameDataDao playerGameData;
    public static BanedDao baned;

    public static void initDao() {
        try {
            Class.forName(R.INSTANCE.getConfig().getString("jdbc.driver"));
            String databaseUrl = R.INSTANCE.getConfig().getString("jdbc.url").replace("!dir!", R.INSTANCE.getDataFolder().toString());
            conn = new JdbcConnectionSource(databaseUrl);

            games = new GamesDao(conn);
            playerData = new PlayerDataDao(conn);
            playerGameData = new PlayerGameDataDao(conn);
            baned = new BanedDao(conn);

            TableUtils.createTableIfNotExists(conn, Games.class);
            TableUtils.createTableIfNotExists(conn, PlayerData.class);
            TableUtils.createTableIfNotExists(conn, PlayerGameData.class);
            TableUtils.createTableIfNotExists(conn, Baned.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
