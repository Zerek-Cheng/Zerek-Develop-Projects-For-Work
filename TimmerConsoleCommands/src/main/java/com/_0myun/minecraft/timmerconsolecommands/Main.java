package com._0myun.minecraft.timmerconsolecommands;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class Main extends JavaPlugin {
    static Main INSTANCE;

    List<CommandTask> tasks;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();

        reloadTasks();
        new TaskRunner().runTaskTimer(this, 10, 10);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public void reloadTasks() {
        this.tasks = new ArrayList<>();
        ConfigurationSection tasks = getConfig().getConfigurationSection("task");
        tasks.getKeys(false).forEach(key -> {
            ConfigurationSection taskConfig = tasks.getConfigurationSection(key);
            this.tasks.add(new CommandTask(key, getYYYYMMDDHMS(taskConfig.getInt("hour"), taskConfig.getInt("minute"))));
        });
    }

    public String getYYYYMMDD() {
        return new SimpleDateFormat("yyyy年MM月dd日").format(new Date((System.currentTimeMillis())));
    }

    public long getYYYYMMDDHMS(int hour, int minute) {
        try {
            return new SimpleDateFormat("yyyy年MM月dd日 h:m:s").parse(getYYYYMMDD() + " " + hour + ":" + minute + ":00").getTime();
        } catch (ParseException e) {
            return System.currentTimeMillis() + 11111;
        }
    }

    public boolean existTask(String name) {
        return getConfig().isSet("task." + name);
    }

    public ConfigurationSection getTask(String name) {
        return getConfig().getConfigurationSection("task."+name);
    }

    public synchronized boolean hasRunTask(String name) {
        return getTask(name).getString("last").equalsIgnoreCase(getYYYYMMDD());
    }

    public void setRunTask(String name) {
        getTask(name).set("last", getYYYYMMDD());
    }
}
