package com.WeiBoss.bossshoptr.Database;

import com.WeiBoss.bossshoptr.Constructor.Log;
import com.WeiBoss.bossshoptr.File.Config;
import com.WeiBoss.bossshoptr.Main;
import com.WeiBoss.bossshoptr.Util.DateUtil;
import com.WeiBoss.bossshoptr.Util.ItemConvertUtil;
import com.WeiBoss.bossshoptr.Util.SQLUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class SQLManager {
    private static Main plugin = Main.instance;
    private static ItemConvertUtil convertUtil = new ItemConvertUtil(plugin.getVersion());

    public static void createTable() {
        if (Config.SQLEnable) {
            plugin.sqlBool = true;
            String table = "CREATE TABLE IF NOT EXISTS log ( id INT NOT NULL AUTO_INCREMENT , player VARCHAR(50) NOT NULL , commodity TEXT NOT NULL , date DATETIME NOT NULL , shop VARCHAR(30) NOT NULL , name VARCHAR(30) NOT NULL , PRIMARY KEY (id)) ENGINE = InnoDB";
            plugin.sqlUtil = new SQLUtil(Config.Host, Config.Port, Config.Database, Config.Users, Config.Password);
            plugin.sqlUtil.openConnection();
            plugin.sqlUtil.updateSQL(table);
            plugin.sqlUtil.closeConnection();
            plugin.sendMsg("连接数据库成功!");
        } else {
            plugin.sqlBool = false;
            plugin.sendMsg("未开启数据库连接!");
        }
    }

    public static void readAllLog() {
        if (plugin.sqlBool) {
            String select = "SELECT * FROM log";
            String delete = "DELETE FROM log WHERE id=";
            plugin.sqlUtil.openConnection();
            ResultSet set = plugin.sqlUtil.querySQL(select);
            try {
                while (set.next()) {
                    int id = set.getInt("id");
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(set.getString("player")));
                    ItemStack item = convertUtil.convert(set.getString("commodity"));
                    Date date = set.getTimestamp("date");
                    String shop = set.getString("shop");
                    String name = set.getString("name");
                    if (DateUtil.getLastHour(date) <= 0) {
                        plugin.sqlUtil.updateSQL(delete + "'" + id + "'");
                    } else {
                        plugin.logList.add(new Log(id, player, item, date, shop, name));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addLog(Player p, ItemStack item, String shop, String name) {
        if (plugin.sqlBool) {
            UUID uuid = p.getUniqueId();
            String items = convertUtil.convert(item);
            Date date = DateUtil.getNowDate();
            String dates = DateUtil.getDateToStr(date);
            String insert = "INSERT INTO log(player,commodity,date,shop,name) VALUES ('" + uuid + "','" + items + "','" + dates + "','" + shop + "','" + name + "')";
            String select = "SELECT id FROM log WHERE player='" + uuid + "' AND commodity='" + items + "' AND date='" + dates + "' AND shop='" + shop + "' AND name='" + name + "'";
            plugin.sqlUtil.openConnection();
            plugin.sqlUtil.updateSQL(insert);
            ResultSet set = plugin.sqlUtil.querySQL(select);
            try {
                if (set.next()) {
                    int id = set.getInt("id");
                    plugin.logList.add(new Log(id, p, item, date, shop, name));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                plugin.sqlUtil.closeConnection();
            }
        }
    }
}