package com._0myun.minecraft.timmerconsolecommands;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class CommandTask {

    String name;
    long time;

    public CommandTask(String name, long time) {
        this.name = name;
        this.time = time;
    }

    public boolean canRun() {
        //System.out.println("time = " + time);
        if (Main.INSTANCE.hasRunTask(name)) return false;
        return System.currentTimeMillis() >= time;
    }

    public void run() {
        if (!Main.INSTANCE.hasRunTask(name));
        ConfigurationSection task = Main.INSTANCE.getTask(this.name);
        List<String> commands = task.getStringList("command");
        String command = commands.get((new Random()).nextInt(commands.size()));

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        Main.INSTANCE.getLogger().log(Level.INFO, "成功执行每日命令" + this.name);

        Main.INSTANCE.setRunTask(this.name);
    }
}
