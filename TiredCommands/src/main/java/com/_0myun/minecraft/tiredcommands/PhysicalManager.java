package com._0myun.minecraft.tiredcommands;

import org.bukkit.configuration.ConfigurationSection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PhysicalManager {
    private static ConfigurationSection getData() {
        return Main.plugin.getConfig().getConfigurationSection("data");
    }

    public static ConfigurationSection getUser(UUID uuid) {
        ConfigurationSection user = getData().getConfigurationSection(uuid.toString());
        if (user == null) {
            getData().createSection(uuid.toString());
            user = getData().getConfigurationSection(uuid.toString());
            user.set("lastTime", new SimpleDateFormat("yyyyMMdd").format(new Date()));
            user.set("physical", getDaily());
        }
        return user;
    }

    public static String getLast(UUID uuid) {
        return getUser(uuid).getString("lastTime");
    }

    public static int getPhysical(UUID uuid) {
        return getUser(uuid).getInt("physical");
    }

    public static void takePhysical(UUID uuid, int amount) {
        setPhysical(uuid, getPhysical(uuid) - amount);
        if (getPhysical(uuid)>getMax()) setPhysical(uuid,getMax());
    }

    public synchronized static void setPhysical(UUID uuid, int amount) {
        getUser(uuid).set("physical", amount);
    }

    public static void refresh(UUID uuid) {
        String last = getLast(uuid);
        String now = new SimpleDateFormat("yyyyMMdd").format(new Date());
        if (last.equalsIgnoreCase(now)) return;
        setPhysical(uuid, getDaily());
        getUser(uuid).set("lastTime", now);
        System.out.println(uuid + "刷新");
    }

    public static int getMax() {
        return Main.plugin.getConfig().getInt("physicalMax");
    }

    public static int getDaily() {
        return Main.plugin.getConfig().getInt("physicalDaily");
    }
}
