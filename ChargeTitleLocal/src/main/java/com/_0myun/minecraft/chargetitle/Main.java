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
        this.saveConfig();
    }

    public void onEnable() {
        this.ispon = this.hookPlayerPoints();
        this.CreateConfig();
        this.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this);
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

    }

    public void mysqladd(OfflinePlayer p, String prefix) {
        getConfig().set("data." + p.getUniqueId().toString(), prefix);
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
        getConfig().addDefault("data." + p.getUniqueId().toString(), "");
        return getConfig().getString("data." + p.getUniqueId().toString());
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
                p.sendMessage("这是你输入的长度 ->" + ChatColor.stripColor((String) args[1]).length());
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
        this.saveConfig();
        return false;
    }
}

