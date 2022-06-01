package com._0myun.minecraft.skillapi.vexskillkey;

import lk.vexview.api.VexViewAPI;
import lk.vexview.hud.VexImageShow;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;


public final class R extends JavaPlugin {
    @Getter
    public static boolean DEBUG = false;
    public static R INSTANCE;
    @Getter
    private final FileConfiguration data = new YamlConfiguration();
    @Getter
    private final LinkedHashMap<String, Integer> playerSkillImageIdMap = new LinkedHashMap<>();

    @Override
    public void onEnable() {
        INSTANCE = this;
        load();
        DEBUG = getConfig().getBoolean("debug");

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginCommand("vsk").setExecutor(new CommandDealer());

        new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    getLogger().info("定制插件找灵梦云科技q2025255093");
                }
            }
        }.runTaskLater(this, 1);
        cdid = getConfig().getInt("cooldownIdStart");
    }

    @Override
    public void onDisable() {
        try {
            data.save(this.getDataFolder().toString() + "/data.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        saveDefaultConfig();
        saveResource("data.yml", false);
        try {
            this.data.load(this.getDataFolder().toString() + "/data.yml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String lang(String lang) {
        return getConfig().getString("lang." + lang);
    }

    public boolean isSkillOpen(UUID uuid, int index) {
        return data.getBoolean(uuid.toString() + "-" + index, false);
    }

    public void setSkillOpen(UUID uuid, int index, boolean open) {
        data.set(uuid.toString() + "-" + index, open);
    }

    public int getKey(int index) {
        return getConfig().getInt("key." + index);
    }

    public int keyToIndex(int key) {
        for (String index : getConfig().getConfigurationSection("key").getKeys(false)) {
            if (getConfig().getInt("key." + index) == key) return Integer.parseInt(index);
        }
        return -1;
    }

    public String getSkillName(String clazz, int index) {
        return getConfig().getString("class." + clazz + "." + index, "default");
    }

    public int getSkillIndex(String clazz, String skill) {
        for (int i = 1; i <= getConfig().getConfigurationSection("class." + clazz).getKeys(false).size(); i++) {
            if (getSkillName(clazz, i).equalsIgnoreCase(skill)) return i;
        }
        return -1;
    }

    public VexImageShow getBg() {
        return new VexImageShow(
                57001
                , getConfig().getString("gui.bg.url")
                , getConfig().getInt("gui.bg.x")
                , getConfig().getInt("gui.bg.y")
                , getConfig().getInt("gui.bg.w")
                , getConfig().getInt("gui.bg.h")
                , getConfig().getInt("gui.bg.w")
                , getConfig().getInt("gui.bg.h")
                , 0);
    }

    public void playerUnInit(Player p) {
        try {
            VexViewAPI.removeHUD(p, 57001);
            String name = SkillAPIUtils.getMainClassName(p);
            for (String index : getConfig().getConfigurationSection("class." + name).getKeys(false)) {
                if (this.playerSkillImageIdMap.containsKey(p.getName() + "-" + index))
                    System.out.println(" this.playerSkillImageIdMap.get(p.getName() + \"-\" + index) = " + this.playerSkillImageIdMap.get(p.getName() + "-" + index));
                VexViewAPI.removeHUD(p, this.playerSkillImageIdMap.get(p.getName() + "-" + index));
            }
        } catch (Exception e) {
            if (DEBUG) e.printStackTrace();
        }
    }

    public void playerInit(Player p) {
        VexViewAPI.sendHUD(p, R.INSTANCE.getBg());
        sendPlayerSkillsShow(p);
    }

    public void sendPlayerSkillsShow(Player p) {
        String name = SkillAPIUtils.getMainClassName(p);
        ConfigurationSection classConfig = getConfig().getConfigurationSection("class." + name);
        for (String index : classConfig.getKeys(false)) {
            VexImageShow skillImageShow = new VexImageShow(
                    57002 + new ArrayList<>(playerSkillImageIdMap.keySet()).indexOf(p.getName() + "-" + index)
                    , isSkillOpen(p.getUniqueId(), Integer.valueOf(index))
                    ? getConfig().getString("skill." + getSkillName(name, Integer.valueOf(index)))
                    : getConfig().getString("skill.default")
                    , getConfig().getInt("gui.x")
                    , getConfig().getInt("gui.y") + (Integer.valueOf(index) - 1) * getConfig().getInt("gui.y-interval")
                    , getConfig().getInt("skill-img-size")
                    , getConfig().getInt("skill-img-size")
                    , getConfig().getInt("skill-img-size")
                    , getConfig().getInt("skill-img-size")
                    , 0
            );
            if (DEBUG) {
                System.out.println("skillImageShow = " + skillImageShow.getID());
                System.out.println("skillImageShow = " + skillImageShow.getNetCode());
            }
            this.playerSkillImageIdMap.put(p.getName() + "-" + index, skillImageShow.getID());
            VexViewAPI.sendHUD(p, skillImageShow);
        }
    }

    int cdid = 2400;

    public void sendPlayerSkillCooldownShow(Player p, int index, int cd) {
        int skillImgSize = getConfig().getInt("skill-img-size");
        int skillImgYInterval = getConfig().getInt("gui.y-interval");
        if (cd == 0) return;
        int amount = getConfig().getInt("gui.img-cd-amount");
        int pieceOfY = Double.valueOf(skillImgSize / amount).intValue();
        double pieceOfCD = (double) cd / amount;
        double tickOfPiece = Double.valueOf(20d * pieceOfCD);
        if (DEBUG) {
            System.out.println("p.getName() = " + p.getName());
            System.out.println("index = " + index);
            System.out.println("cd = " + cd);
            System.out.println("pieceOfY = " + pieceOfY);
            System.out.println("pieceOfCD = " + pieceOfCD);
            System.out.println("tickOfPiece = " + tickOfPiece);
        }
        for (int i = 0; i < amount; i++) {
            double d = (cd * 20 - tickOfPiece * i) / 20;
            VexImageShow cooldownImageShow = new VexImageShow(
                    //57002 + new ArrayList<>(playerSkillImageIdMap.keySet()).indexOf(p.getName() + "-" + index) + (i + 1) * 10000
                    //new Random().nextInt(1000)
                    cdid
                    , getConfig().getString("gui.img-cd-url")
                    , getConfig().getInt("gui.x")
                    , getConfig().getInt("gui.y")
                    + skillImgSize - (i + 1) * pieceOfY
                    + (index - 1) * skillImgYInterval
                    , skillImgSize, pieceOfY
                    , skillImgSize, pieceOfY
                    , Double.valueOf(d).intValue() > 0 ? Double.valueOf(d).intValue() : 1
            );
            if (DEBUG) {
                System.out.println("cooldownImageShow = " + cooldownImageShow.getID());
                System.out.println("cooldownImageShow = " + cooldownImageShow.getNetCode());
            }
            VexViewAPI.sendHUD(p, cooldownImageShow);
            cdid++;
        }
    }
}
