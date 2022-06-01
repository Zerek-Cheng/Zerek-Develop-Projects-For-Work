/*
 * Decompiled with CFR 0_133.
 */
package info.TrenTech.EasyKits.SQL;

import com.Zrips.CMI.CMI;
import info.TrenTech.EasyKits.Utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SQLPlayers
        extends SQLUtils {
    private static Object lock = new Object();

    public static boolean tableExist(String playerUUID) {
        try {
            Statement statement = SQLUtils.getConnection().createStatement();
            DatabaseMetaData md = statement.getConnection().getMetaData();
            ResultSet rs = md.getTables(null, null, playerUUID, null);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException statement) {
            // empty catch block
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createTable(String playerUUID) {
        Object object = lock;
        synchronized (object) {
            try {
                PreparedStatement statement = SQLUtils.prepare("CREATE TABLE `" + playerUUID + "`( id INTEGER PRIMARY KEY, Kit TEXT, Cooldown TEXT, Limits INTEGER, Obtained TEXT)");
                statement.executeUpdate();
                Logger.getGlobal().info("Creating player tables");
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void deleteTable(String playerUUID) {
        Object object = lock;
        synchronized (object) {
            try {
                PreparedStatement statement = SQLUtils.prepare("DROP TABLE `" + playerUUID + "`");
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static void createLimitTable() {
        Object object = lock;
        synchronized (object) {
            try {
                PreparedStatement statement = SQLUtils.prepare("CREATE TABLE `limit`( id INTEGER PRIMARY KEY, kit TEXT, ip TEXT, has TEXT);");
                statement.executeUpdate();
                Logger.getGlobal().info("Creating limit tables");
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean kitExist(String playerUUID, String kitName) {
        try {
            PreparedStatement statement = SQLUtils.prepare("SELECT * FROM `" + playerUUID + "`");
            ResultSet rs = statement.executeQuery();
            do {
                if (rs.next()) continue;
                return false;
            } while (!rs.getString("Kit").equalsIgnoreCase(kitName));
            return true;
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void addKit(String playerUUID, String kitName, String cooldown, int limit, String obtainedBefore) {
        Object object = lock;
        synchronized (object) {
            try {
                PreparedStatement statement = SQLUtils.prepare("INSERT into `" + playerUUID + "` (Kit, Cooldown, Limits, Obtained) VALUES (?, ?, ?, ?)");
                statement.setString(1, kitName);
                statement.setString(2, cooldown);
                statement.setInt(3, limit);
                statement.setString(4, obtainedBefore);
                statement.executeUpdate();


                statement = SQLUtils.prepare("INSERT into `limit` (`kit`, `ip`, `has`) VALUES (?, ?, ?)");
                statement.setString(1, kitName);
                statement.setString(2, CMI.getInstance().getPlayerManager().getUser(playerUUID).getLastIp());
                statement.setInt(3, limit);
                statement.executeUpdate();

            } catch (Exception e) {
                //Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setDateObtained(String playerUUID, String kitName, String cooldown) {
        Object object = lock;
        synchronized (object) {
            try {
                PreparedStatement statement = SQLUtils.prepare("UPDATE `" + playerUUID + "` SET Cooldown = ? WHERE Kit = ?");
                statement.setString(1, cooldown);
                statement.setString(2, kitName);
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setObtained(String playerUUID, String kitName, String obtainedBefore) {
        Object object = lock;
        synchronized (object) {
            try {
                PreparedStatement statement = SQLUtils.prepare("UPDATE `" + playerUUID + "` SET Obtained = ? WHERE Kit = ?");
                statement.setString(1, obtainedBefore);
                statement.setString(2, kitName);
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setLimit(String playerUUID, String kitName, int limit) {
        Object object = lock;
        synchronized (object) {
            try {
                PreparedStatement statement = SQLUtils.prepare("UPDATE `limit` SET `has` = ? WHERE `kit` = ? AND `ip` = ?");
                statement.setInt(1, limit);
                statement.setString(2, kitName);
                statement.setString(3, CMI.getInstance().getPlayerManager().getUser(playerUUID).getLastIp());
                statement.executeUpdate();
            } catch (Exception e) {
                //Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static String getDateObtained(String playerUUID, String kitName) {
        try {
            PreparedStatement statement = SQLUtils.prepare("SELECT * FROM `" + playerUUID + "`");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (!rs.getString("Kit").equalsIgnoreCase(kitName)) continue;
                return rs.getString("Cooldown");
            }
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return "0";
    }

    public static int getLimit(String playerUUID, String kitName) {
        try {
            PreparedStatement statement = SQLUtils.prepare("SELECT * FROM `limit` WHERE `kit`= ? AND `ip`= ?");
            statement.setString(1, kitName);
            statement.setString(2, CMI.getInstance().getPlayerManager().getUser(playerUUID).getLastIp());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                return rs.getInt("has");
            }
        } catch (Exception e) {
            //Utils.getLogger().severe(e.getMessage());
        }
        return 0;
    }

    public static String getObtained(String playerUUID, String kitName) {
        try {
            PreparedStatement statement = SQLUtils.prepare("SELECT * FROM `" + playerUUID + "`");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (!rs.getString("Kit").equalsIgnoreCase(kitName)) continue;
                return rs.getString("Obtained");
            }
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return "FALSE";
    }

    public static List<String> getPlayerList() {
        ArrayList<String> playerList = new ArrayList<String>();
        try {
            Statement statement = SQLUtils.getConnection().createStatement();
            DatabaseMetaData md = statement.getConnection().getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                if (rs.getString(3).equalsIgnoreCase("kits") || rs.getString(3).equalsIgnoreCase("limit")) continue;
                playerList.add(rs.getString(3));
            }
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return playerList;
    }
}

