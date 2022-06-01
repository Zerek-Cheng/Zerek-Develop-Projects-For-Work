package com.lmyun.event.pointchanger;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PointManager {
    private static YamlConfiguration data;

    public static void load() {
        PointManager.data = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder().getPath() + "/Data.yml"));
    }

    public static int getPoint(String uName) {
        return data.getInt(uName);
    }

    public static void setPoint(String uName, int point) {
        data.set(uName, point);
    }

    public static boolean hasPoint(String uName, int point) {
        return getPoint(uName) >= point;
    }

    public static void addPoint(String uName, int point) {
        setPoint(uName, getPoint(uName) + point);
    }

    public static void save() {
        try {
            data.save(new File(Main.getPlugin().getDataFolder().getPath() + "/Data.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
