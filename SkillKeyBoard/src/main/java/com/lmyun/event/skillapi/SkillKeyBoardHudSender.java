package com.lmyun.event.skillapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SkillKeyBoardHudSender extends BukkitRunnable {
    Player player;
    SkillKeyBoard plugin;

    public SkillKeyBoardHudSender(Player p, SkillKeyBoard plugin) {
        this.player = p;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!Bukkit.getOfflinePlayer(player.getName()).isOnline()) {
            this.cancel();
        }
        plugin.sendPlayerHud(player);
    }
}
