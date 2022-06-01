package com._0myun.minecraft.peacewarrior;

import com._0myun.minecraft.peacewarrior.dao.BattleDao;
import com._0myun.minecraft.peacewarrior.dao.PlayerDataDao;
import com._0myun.minecraft.peacewarrior.dao.PlayerInvDao;
import com._0myun.minecraft.peacewarrior.dao.SignDao;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerInv;
import com._0myun.minecraft.peacewarrior.dao.data.Sign;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBManager {
    public static JdbcConnectionSource conn;
    public static SignDao signDao;
    public static BattleDao battleDao;
    public static PlayerDataDao playerDataDao;
    public static PlayerInvDao playerInvDao;

    public static void initDao() {
        try {
            signDao = new SignDao(conn, Sign.class);
            if (!signDao.isTableExists()) {
                TableUtils.createTable(signDao);
                R.INSTANCE.getLogger().info("创建表sign");
            }
            battleDao = new BattleDao(conn, Battle.class);
            if (!battleDao.isTableExists()) {
                TableUtils.createTable(battleDao);
                R.INSTANCE.getLogger().info("创建表battle");
            }
            playerDataDao = new PlayerDataDao(conn, PlayerData.class);
            if (!playerDataDao.isTableExists()) {
                TableUtils.createTable(playerDataDao);
                R.INSTANCE.getLogger().info("创建表playerdata");
            }

            playerInvDao = new PlayerInvDao(conn, PlayerInv.class);
            if (!playerInvDao.isTableExists()) {
                TableUtils.createTable(playerInvDao);
                R.INSTANCE.getLogger().info("创建表playerinv");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
