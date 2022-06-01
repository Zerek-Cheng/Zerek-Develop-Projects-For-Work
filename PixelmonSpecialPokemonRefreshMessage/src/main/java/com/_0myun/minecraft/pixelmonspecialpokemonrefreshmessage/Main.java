package com._0myun.minecraft.pixelmonspecialpokemonrefreshmessage;

import com.pixelmonmod.pixelmon.api.spawning.AbstractSpawner;
import com.pixelmonmod.pixelmon.config.PixelmonConfig;
import com.pixelmonmod.pixelmon.spawning.LegendarySpawner;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        new Broadcaster().runTaskTimer(this, getConfig().getInt("every") * 20, getConfig().getInt("every") * 20);
    }

    public long getNeedTime() {
        if (getSpawner() instanceof LegendarySpawner) {
            LegendarySpawner spawner = (LegendarySpawner) getSpawner();
            long next = spawner.nextSpawnTime;
            long now = System.currentTimeMillis();
            return Double.valueOf((next - now) / 1000d).intValue();
        }
        return -1;
    }

    public long getChance() {
        return Double.valueOf(PixelmonConfig.getLegendaryRate() * 100d).intValue();
    }


    public AbstractSpawner getSpawner() {
        return PixelmonSpawning.coordinator.getSpawner(getConfig().getString("spawner"));
    }

    public String getBroadcastMsg() {
        return getConfig().getString("lang")
                .replace("{time}", String.valueOf(getNeedTime()))
                .replace("{chance}", String.valueOf(getChance()));
    }

    public void broadcast() {
        Bukkit.broadcastMessage(getBroadcastMsg());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            sender.sendMessage("reloadok");
            return true;
        }
        broadcast();
        return true;
    }
}
