package com._0myun.minecraft.blockrandomcommands;

import java.sql.*;
import java.util.logging.Level;

public class SqliteManager {
    static Connection c = null;

    public static void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + Main.INSTANCE.getDataFolder().toString() + "/place.db");
            Main.INSTANCE.getLogger().log(Level.INFO, "sqlite load ok");
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("数据库错误");
        }
    }

    public static void createTable() throws SQLException {
        Statement statement = c.createStatement();
        statement.execute("CREATE TABLE \"place\" (\n" +
                "  \"loc\" text NOT NULL,\n" +
                "  PRIMARY KEY (\"loc\")\n" +
                ");\n" +
                "\n" +
                "PRAGMA foreign_keys = true;");
        statement.close();
    }

    public synchronized static boolean exist(String loc) {
        try {
            PreparedStatement statement = c.prepareStatement("SELECT * FROM \"place\" WHERE `loc`=?");
            statement.setString(1, loc);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public synchronized static void add(String loc) {
        if (exist(loc)) return;
        try {
            PreparedStatement statement = c.prepareStatement("insert INTO `place` (`loc`) VALUES (?);");
            statement.setString(1, loc);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
