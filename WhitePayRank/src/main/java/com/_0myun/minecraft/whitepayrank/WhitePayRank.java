/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  com.mc9y.pixelmonpvp.Arena
 *  com.mc9y.pixelmonpvp.PixelmonPvp
 *  com.mc9y.pixelmonpvp.data.PlayerData
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package com._0myun.minecraft.whitepayrank;

import com._0myun.minecraft.whitepayrank.hook.PlaceholderHook;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class WhitePayRank extends JavaPlugin implements Listener {
    public static WhitePayRank INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        new PlaceholderHook().register();
    }
}

