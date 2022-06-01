package com._0myun.minecraft.eventmsg.colorchar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class ColorChar extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("com.0myun.colorchat")) return;
        List<String> colors = Arrays.asList("4", "9", "c", "d", "6", "5", "e", "f", "2", "7", "a", "8", "b", "0", "3", "1");
        String message = e.getMessage();
        e.setMessage("§" + colors.get(new Random().nextInt(colors.size())) + message);

    }
}
