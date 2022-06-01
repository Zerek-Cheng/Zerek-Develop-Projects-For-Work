package com.lmyun.event.regioncontrol;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class RegionListener implements Listener {
    Main plugin;

    public RegionListener(Main main) {
        this.plugin = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String message = e.getMessage();
        String[] commandArray = message.toLowerCase().split(" ");
        final Player p = e.getPlayer();
        Location loc = p.getLocation();
        if (!this.plugin.inRegion(loc)) {
            return;
        }
        boolean bannedCommand = this.plugin.isBannedCommand(commandArray[0]);
        e.setCancelled(bannedCommand);
        if (bannedCommand) {
            e.getPlayer().sendMessage(this.plugin.getLang("lang1"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTped(PlayerCommandPreprocessEvent e) {
        String message = e.getMessage();
        String[] commandArray = message.toLowerCase().split(" ");
        if (commandArray.length < 2) {
            return;
        }
        if (!(commandArray[0].equalsIgnoreCase("/tpa")) && !(commandArray[0].equalsIgnoreCase("/tpahere")) &&
                !(commandArray[0].equalsIgnoreCase("/tp")) && !(commandArray[0].equalsIgnoreCase("/tphere"))) {
            return;
        }
        String playerName = commandArray[1];
        OfflinePlayer pOff = Bukkit.getOfflinePlayer(playerName);
        if (!pOff.isOnline()) {
            return;
        }
        Player p = pOff.getPlayer();
        boolean bannedCommand = this.plugin.isBannedCommand(commandArray[0]);
        e.setCancelled(bannedCommand);
        if (bannedCommand) {
            e.getPlayer().sendMessage(this.plugin.getLang("lang2"));
        }
    }
}
