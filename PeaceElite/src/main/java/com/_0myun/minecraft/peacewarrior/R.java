package com._0myun.minecraft.peacewarrior;

import com._0myun.minecraft.peacewarrior.commands.BaseCommand;
import com._0myun.minecraft.peacewarrior.data.BattleMap;
import com._0myun.minecraft.peacewarrior.data.Position;
import com._0myun.minecraft.peacewarrior.events.PWPluginInitEvent;
import com._0myun.minecraft.peacewarrior.listeners.battle.*;
import com._0myun.minecraft.peacewarrior.listeners.hall.HallClickSignListener;
import com._0myun.minecraft.peacewarrior.listeners.hall.HallInfoSignListener;
import com._0myun.minecraft.peacewarrior.listeners.sys.SysPlayerListener;
import com._0myun.minecraft.peacewarrior.listeners.sys.SysPluginListener;
import com._0myun.minecraft.peacewarrior.listeners.wait.WaitPlayerListener;
import com._0myun.minecraft.peacewarrior.task.battle.AreaDamageTask;
import com._0myun.minecraft.peacewarrior.task.battle.BattleStater;
import com._0myun.minecraft.peacewarrior.task.battle.InfoRefresher;
import com._0myun.minecraft.peacewarrior.task.hall.InfoSignRefreshTask;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.mysql.cj.jdbc.JdbcConnection;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.DriverManager;
import java.util.List;

public final class R extends JavaPlugin {
    static {
        ConfigurationSerialization.registerClass(BattleMap.class);
        ConfigurationSerialization.registerClass(Position.class);
    }

    public static R INSTANCE;
    public static boolean DEBUG;
    private YamlConfiguration lang = new YamlConfiguration();
    private File mapsDir = new File(this.getDataFolder().getPath() + "/map/");

    @Override
    public void onEnable() {
        INSTANCE = this;

        Bukkit.getPluginCommand("pw").setExecutor(new BaseCommand());
        Bukkit.getPluginCommand("pe").setExecutor(new BaseCommand());
        Bukkit.getPluginCommand("pubg").setExecutor(new BaseCommand());

        new BukkitRunnable() {
            @Override
            public void run() {
                getLogger().info("欢迎使用灵梦云科技——和平勇士插件");
                getLogger().info("插件载入中!");
                boolean init = R.this.init();
                if (!init) {
                    getLogger().warning("插件载入异常,已关闭!");
                    Bukkit.getPluginManager().disablePlugin(R.this);
                    return;
                }
                Bukkit.getPluginManager().registerEvents(new SysPluginListener(), R.this);
                getLogger().info("系统——系统监听器注册");
                Bukkit.getPluginManager().registerEvents(new SysPlayerListener(), R.this);
                getLogger().info("系统——玩家监听器注册");
                Bukkit.getPluginManager().registerEvents(new HallInfoSignListener(), R.this);
                getLogger().info("大厅——信息牌监听器注册");
                Bukkit.getPluginManager().registerEvents(new HallClickSignListener(), R.this);
                getLogger().info("大厅——加入游戏监听器注册");
                Bukkit.getPluginManager().registerEvents(new WaitPlayerListener(), R.this);
                getLogger().info("等待大厅——玩家监听器注册");
                Bukkit.getPluginManager().registerEvents(new BattleStartListener(), R.this);
                getLogger().info("游戏——战斗开始监听器注册");
                Bukkit.getPluginManager().registerEvents(new FlyKeyBoardListener(), R.this);
                getLogger().info("游戏——跳伞监听器注册");
                Bukkit.getPluginManager().registerEvents(new BattleFlyEndListener(), R.this);
                getLogger().info("游戏——跳伞阶段结束监听器注册");
                Bukkit.getPluginManager().registerEvents(new MapKeyBoardListener(), R.this);
                getLogger().info("游戏——地图监听器注册");
                Bukkit.getPluginManager().registerEvents(new PlayerFailedListener(), R.this);
                getLogger().info("游戏——玩家失败监听器注册");
                Bukkit.getPluginManager().registerEvents(new PlayerWeakListener(), R.this);
                getLogger().info("游戏——玩家虚弱监听器注册");
                Bukkit.getPluginManager().registerEvents(new PlayerOpenChestListener(), R.this);
                getLogger().info("游戏——玩家打开补给箱监听器注册");
                Bukkit.getPluginManager().registerEvents(new BattleFinishListener(), R.this);
                getLogger().info("游戏——战斗结束监听器注册");

                new InfoSignRefreshTask().runTaskTimerAsynchronously(R.this, 20l, 20l);
                getLogger().info("大厅——信息牌刷新任务建立");
                new InfoRefresher().runTaskTimerAsynchronously(R.this, 100l, 100l);
                getLogger().info("游戏——场次数据刷新任务建立");
                new BattleStater().runTaskTimerAsynchronously(R.this, 100l, 10l);
                getLogger().info("游戏——场次启动任务建立");
                new AreaDamageTask().runTaskTimerAsynchronously(R.this, 20l, 20l);
                getLogger().info("游戏——场次安全区扣血任务建立");

                Bukkit.getPluginManager().callEvent(new PWPluginInitEvent());
                getLogger().info("通知初始化事件");
            }
        }.runTaskLater(this, 10);
        new PluginVariable().register();
    }

    public boolean init() {
        reloadConfig();
        DEBUG = getConfig().getBoolean("debug", false);

        getLogger().info("插件初始化!");
        saveDefaultConfig();
        saveResource("lang.yml", false);
        saveResource("map/battle.yml", false);
        try {
            lang.load(getDataFolder() + "/lang.yml");
            getLogger().info("语言文件加载成功!");

            MapManager.maps.clear();
            for (File file : mapsDir.listFiles()) {
                YamlConfiguration mapConfig = new YamlConfiguration();
                mapConfig.load(file);
                BattleMap map = (BattleMap) mapConfig.get("map");
                MapManager.maps.add(map);
                getLogger().info(String.format("加载地图: %s(%s)", map.getDisplay(), map.getName()));
            }

            Class.forName(getConfig().getString("jdbc.driver"));

            DBManager.conn = new JdbcConnectionSource(getConfig().getString("jdbc.url"), getConfig().getString("jdbc.username"), getConfig().getString("jdbc.password"));
            getLogger().info("数据库连接成功!");
            DBManager.initDao();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public void save() {
        try {
            DBManager.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        save();
    }

    public String lang(String path) {
        return this.lang.getString(path, path);
    }

    public List<String> langs(String path) {
        return this.lang.getStringList(path);
    }
}
