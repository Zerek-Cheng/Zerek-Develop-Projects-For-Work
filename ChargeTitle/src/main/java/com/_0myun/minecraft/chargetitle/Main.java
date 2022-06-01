/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  net.milkbowl.vault.chat.Chat
 *  org.black_ixx.playerpoints.PlayerPoints
 *  org.black_ixx.playerpoints.PlayerPointsAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.ServicesManager
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com._0myun.minecraft.chargetitle;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
import java.util.HashMap;

public class Main
        extends JavaPlugin
        implements Listener {
    public static Main plugin;
    private String ip;
    private String databaseName;
    private String userName;
    private String userPassword;
    private Connection connection;
    private String port;
    public static Main instance;
    boolean hasNull = false;
    HashMap<String, String> HashMap = new HashMap();
    private PlayerPoints Points;
    private boolean ispon = false;

    private boolean hookPlayerPoints() {
        plugin = this;
        Plugin plugin = this.getServer().getPluginManager().getPlugin("PlayerPoints");
        this.Points = PlayerPoints.class.cast(plugin);
        if (this.Points != null) {
            return true;
        }
        return false;
    }

    public void onDisable() {
    }

    public void onEnable() {
        this.ispon = this.hookPlayerPoints();
        this.CreateConfig();
        this.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this);
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + this.getConfig().getString("mysql.ip") + "/" + this.getConfig().getString("mysql.dbname"), this.getConfig().getString("mysql.dbuser"), this.getConfig().getString("mysql.dbpass"));
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `vipname` (`player` varchar(64) NOT NULL,`prefix` varchar(1024) DEFAULT NULL,`qd2` varchar(2) DEFAULT NULL,`qd3` varchar(2) DEFAULT NULL,`qd4` varchar(2) DEFAULT NULL,`qd5` varchar(2) DEFAULT NULL,`qd6` varchar(2) DEFAULT NULL,`qd7` varchar(2) DEFAULT NULL,`qd8` varchar(2) DEFAULT NULL,`qd9` varchar(2) DEFAULT NULL,`qd10` varchar(2) DEFAULT NULL,`qd11` varchar(2) DEFAULT NULL,`qd12` varchar(2) DEFAULT NULL,`qd13` varchar(2) DEFAULT NULL,`qd14` varchar(2) DEFAULT NULL,`qd15` varchar(2) DEFAULT NULL,`qd16` varchar(2) DEFAULT NULL,`qd17` varchar(2) DEFAULT NULL,`qd18` varchar(2) DEFAULT NULL,`qd19` varchar(2) DEFAULT NULL,`qd20` varchar(2) DEFAULT NULL,`qd21` varchar(2) DEFAULT NULL,`qd22` varchar(2) DEFAULT NULL,`qd23` varchar(2) DEFAULT NULL,`qd24` varchar(2) DEFAULT NULL,`qd25` varchar(2) DEFAULT NULL,`qd26` varchar(2) DEFAULT NULL,`qd27` varchar(2) DEFAULT NULL,`qd28` varchar(2) DEFAULT NULL,`qd29` varchar(2) DEFAULT NULL,`qd30` varchar(2) DEFAULT NULL,`qd31` varchar(2) DEFAULT NULL,PRIMARY KEY (`player`)) ENGINE=MyISAM DEFAULT CHARSET=gbk;");
        } catch (SQLException e1) {
            Bukkit.getConsoleSender().sendMessage("连接数据库失败！");
        }
        new Variable().hook();
    }

    public void say(String s) {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();
        sender.sendMessage(s);
    }

    public void CreateConfig() {
        saveDefaultConfig();
        this.reloadConfig();
    }

    public void mysql(Player p) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + this.getConfig().getString("mysql.ip") + "/" + this.getConfig().getString("mysql.dbname"), this.getConfig().getString("mysql.dbuser"), this.getConfig().getString("mysql.dbpass"));
            Statement statement = conn.createStatement();
            String sql = "select * from vipname where player='" + p.getName() + "' ";
            ResultSet rs = statement.executeQuery(sql);
            if (!rs.next()) {
                sql = "INSERT INTO vipname VALUES ('" + p.getName() + "', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' )";
                statement.executeUpdate(sql);
                sql = "select * from vipname where player='" + p.getName() + "'";
                rs = statement.executeQuery(sql);
                rs.next();
            }
        } catch (SQLException conn) {
            // empty catch block
        }
    }

    public void mysqladd(OfflinePlayer p, String prefix) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + this.getConfig().getString("mysql.ip") + "/" + this.getConfig().getString("mysql.dbname"), this.getConfig().getString("mysql.dbuser"), this.getConfig().getString("mysql.dbpass"));
            Statement statement = conn.createStatement();
            String sql = "UPDATE vipname SET prefix = '" + prefix + "' WHERE player = '" + p.getName() + "'";
            statement.executeUpdate(sql);
        } catch (SQLException conn) {
            // empty catch block
        }
    }

    public static String decodeUnicode(String unicode) {
        StringBuffer sb = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        int i = 1;
        while (i < hex.length) {
            int data = Integer.parseInt(hex[i], 16);
            sb.append((char) data);
            ++i;
        }
        return sb.toString();
    }

    public static String unicode(String source) {
        if (1 == 1) return source;
        StringBuffer sb = new StringBuffer();
        char[] source_char = source.toCharArray();
        String unicode = null;
        int i = 0;
        while (i < source_char.length) {
            unicode = Integer.toHexString(source_char[i]);
            if (unicode.length() <= 2) {
                unicode = "00" + unicode;
            }
            sb.append("§u" + unicode);
            ++i;
        }
        System.out.println(sb);
        return sb.toString();
    }

    public String getprefix(OfflinePlayer p) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + this.getConfig().getString("mysql.ip") + "/" + this.getConfig().getString("mysql.dbname"), this.getConfig().getString("mysql.dbuser"), this.getConfig().getString("mysql.dbpass"));
            Statement statement = conn.createStatement();
            String sql = "select prefix from vipname where player='" + p.getName() + "'";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            return "";
        }
        return "";
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (this.HashMap.get(p.getName()) != null) {
            this.mysql(p);
            p.sendMessage("这是你输入的长度 ->" + ChatColor.stripColor((String) e.getMessage()).length());
            p.sendMessage("这是设置的长度 ->" + this.getConfig().getInt("lenth"));
            if (ChatColor.stripColor((String) e.getMessage()).length() > this.getConfig().getInt("lenth")) {
                p.sendMessage("§c长度过长！");
                e.setCancelled(true);
                return;
            }
            this.mysqladd(p, Main.unicode(e.getMessage().replace("&", "§")));
            p.sendMessage("§a§l成功修改称号");
            this.HashMap.remove(p.getName());
            e.setCancelled(true);
            return;
        }
        String prefix = this.getprefix(p).replace("§", "\\");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("VIPName")) {
            if (args.length == 0) {
                int i = 0;
                while (i < this.getConfig().getStringList("Message").size()) {
                    sender.sendMessage(((String) this.getConfig().getStringList("Message").get(i)).replace("&", "§"));
                    ++i;
                }
                if (sender.isOp()) {
                    sender.sendMessage("§6/VIPName reload 重载配置文件");
                    sender.sendMessage("§6/VIPName remove <name> 删除玩家的称号 ");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("remove") && sender.isOp()) {
                this.mysqladd(Bukkit.getOfflinePlayer(args[1]), "");
                sender.sendMessage("§c删除成功~");
            }
/*            if (args[0].equalsIgnoreCase("buy")) {
                if (!sender.hasPermission("vipname.op") && this.Points.getAPI().look(sender.getName()) < this.getConfig().getInt("points")) {
                    sender.sendMessage("§c你的点券不足");
                    return true;
                }
                if (this.HashMap.get(sender.getName()) != null) {
                    sender.sendMessage("§c你正在更改称号，请勿重复操作！");
                    return true;
                }
                this.Points.getAPI().take(sender.getName(), this.getConfig().getInt("points"));
                this.HashMap.put(sender.getName(), "1");
                sender.sendMessage("§d购买成功！请在聊天框内输入称号>");
            }*/
            if (args[0].equalsIgnoreCase("reload")) {
                File file;
                if (!this.getDataFolder().exists()) {
                    this.getDataFolder().mkdir();
                    this.reloadConfig();
                }
                if (!(file = new File(this.getDataFolder(), "config.yml")).exists()) {
                    this.saveDefaultConfig();
                }
                this.reloadConfig();
                sender.sendMessage("§c配置文件重载成功");
            }
            if (args[0].equalsIgnoreCase("set") && args.length == 2) {
                Player p = (Player) sender;
                if (!sender.hasPermission("ChargeTitle.high") && this.Points.getAPI().look(sender.getName()) < this.getConfig().getInt("points")) {
                    sender.sendMessage("§c你的点券不足");
                    return true;
                }
                p.sendMessage("这是你输入的长度 ->" + ChatColor.stripColor((String)args[1]).length());
                p.sendMessage("这是设置的长度 ->" + this.getConfig().getInt("lenth"));
                if (ChatColor.stripColor((String) args[1]).length() > this.getConfig().getInt("lenth")) {
                    p.sendMessage("§c长度过长！");
                    return true;
                }
                this.mysqladd(p, Main.unicode(args[1].replace("&", "§")));
                p.sendMessage("§a§l成功修改称号");

                this.Points.getAPI().take(sender.getName(), this.getConfig().getInt("points"));
                this.mysqladd(Bukkit.getPlayer(sender.getName()), Main.unicode(args[1].replace("&", "§")));
                sender.sendMessage("§d购买成功！");
            }
        }
        return false;
    }
}

