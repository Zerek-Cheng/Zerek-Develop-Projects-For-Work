package com._0myun.minecraft.playerblockexper;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {
    public static Main plugin;
    public final static HashMap<UUID, Integer> sec = new HashMap<>();

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        new PlayerTask().runTaskTimer(this, 20, 20);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location from = e.getFrom().clone();
        from.setY(from.getY() - 1);
        Location to = e.getTo().clone();
        to.setY(to.getY() - 1);
        if (isBlock(to.getBlock()) && !isBlock(from.getBlock()))//从不是到是
            p.sendMessage(getConfig().getString("lang1"));
        if (!isBlock(to.getBlock()) && isBlock(from.getBlock()))//从是到不是
            p.sendMessage(getConfig().getString("lang2"));
    }

    public boolean isBlock(Block block) {
        String[] blockIds = getConfig().getString("block").split(":");
        if (block.getTypeId() != Integer.valueOf(blockIds[0])) return false;
        if (blockIds.length == 2 && blockIds[1].equalsIgnoreCase("*")) blockIds[1] = String.valueOf(block.getData());
        if (blockIds.length == 2 && Integer.valueOf(String.valueOf(block.getData())) != Integer.valueOf(blockIds[1]))
            return false;
        return true;
    }

    public int getTodayMin(UUID p) {
        return getConfig().getInt("data." + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "." + p.toString());
    }

    public void addTodayMin(UUID p) {
        getConfig().set("data." + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "." + p.toString(), getTodayMin(p) + 1);
    }

    public void giveExp(Player p) {
        if (getTodayMin(p.getUniqueId()) >= 30) {
            p.sendMessage(getConfig().getString("lang4"));
            return;
        }
        int exp = getConfig().getInt("exp");
        int resExp = parseLevel(exp, p.getLevel());
        p.giveExp(resExp);
        p.sendMessage(String.format(getConfig().getString("lang3"), String.valueOf(resExp)));
    }

    public int parseLevel(int exp, int level) {
        for (int i = 1; i < level; i++) {
            exp = (int) (exp * 1.1);
        }
        return exp;
    }

}
