package com.WeiBoss.bossshoptr;

import com.WeiBoss.bossshoptr.Commands.MainCommand;
import com.WeiBoss.bossshoptr.Constructor.Log;
import com.WeiBoss.bossshoptr.Constructor.Page;
import com.WeiBoss.bossshoptr.Database.SQLManager;
import com.WeiBoss.bossshoptr.GUI.MainGUI;
import com.WeiBoss.bossshoptr.Listener.BsPurchasedListener;
import com.WeiBoss.bossshoptr.Listener.GuiListener;
import com.WeiBoss.bossshoptr.Util.SQLUtil;
import com.WeiBoss.bossshoptr.Util.WeiUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {
    public static Main instance;
    public File configFile;
    public File messageFile;
    public YamlConfiguration config;
    public YamlConfiguration message;
    public List<String> shop = new ArrayList<>();
    public List<String> itemWhite = new ArrayList<>();
    public List<Log> logList = new ArrayList<>();
    public HashMap<Player, Page> pages = new HashMap<>();
    public boolean sqlBool = false;
    public SQLUtil sqlUtil;
    public MainGUI mainGUI;

    public void onEnable() {
        instance = this;
        getLogger().info("=====================================");
        if ((Bukkit.getPluginManager().getPlugin("BossShop") == null) && (Bukkit.getPluginManager().getPlugin("BossShopPro") == null)) {
            getLogger().info("Hook: 未能成功关联BossShop/BossShopPro,已自动卸载");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (Bukkit.getPluginManager().getPlugin("BossShop") != null) getLogger().info("Hook: 已关联 BossShop");
        if (Bukkit.getPluginManager().getPlugin("BossShopPro") != null) getLogger().info("Hook: 已关联 BossShopPro");
        createFile();
        loadFile();
        readConfig();
        SQLManager.createTable();
        SQLManager.readAllLog();
        mainGUI = new MainGUI(this);
        getCommand("btr").setExecutor(new MainCommand());
        Bukkit.getPluginManager().registerEvents(new BsPurchasedListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
        getLogger().info("加载成功");
        getLogger().info("Author: Wei_Boss | QQ: 1731598625");
        getLogger().info("=====================================");
    }

    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission("bossshoptr.admin")) return;
            if (p.getOpenInventory() != null && p.getOpenInventory().getType() == InventoryType.CHEST && p.getOpenInventory().getTitle().equals(WeiUtil.onReplace(config.getString("MainGUI")))) {
                p.closeInventory();
            }
        }
        getLogger().info("卸载成功");
    }

    public void sendMsg(String text) {
        getLogger().info(text);
    }

    public String getVersion() {
        String packet = Bukkit.getServer().getClass().getPackage().getName();
        return packet.substring(packet.lastIndexOf('.') + 1);
    }

    public void createFile() {
        configFile = new File(getDataFolder(), "config.yml");
        messageFile = new File(getDataFolder(), "message.yml");
        try {
            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                WeiUtil.copyFile(getResource("config.yml"), configFile);
                getLogger().info("File: 已生成 config.yml 文件");
            } else {
                getLogger().info("File: 已加载 config.yml 文件");
            }
            if (!messageFile.exists()) {
                messageFile.getParentFile().mkdirs();
                WeiUtil.copyFile(getResource("message.yml"), messageFile);
                getLogger().info("File: 已生成 message.yml 文件");
            } else {
                getLogger().info("File: 已加载 message.yml 文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFile() {
        config = YamlConfiguration.loadConfiguration(configFile);
        message = YamlConfiguration.loadConfiguration(messageFile);
    }

    public void readConfig() {
        if (config.getStringList("BossShop.Shop").size() != 0)
            shop.addAll(config.getStringList("BossShop.Shop"));
        if (config.getStringList("BossShop.Item").size() != 0)
            itemWhite.addAll(config.getStringList("BossShop.Item"));
    }
}