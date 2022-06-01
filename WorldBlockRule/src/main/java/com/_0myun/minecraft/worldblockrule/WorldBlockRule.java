package com._0myun.minecraft.worldblockrule;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldBlockRule extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        World world = p.getWorld();
        String rule = getConfig().getString(world.getName());
        if (rule.equalsIgnoreCase("break")) {
            e.setCancelled(true);
            p.sendMessage(getConfig().getString("lang1"));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        World world = p.getWorld();
        String rule = getConfig().getString(world.getName());
        if (rule.equalsIgnoreCase("place")) {
            e.setCancelled(true);
            p.sendMessage(getConfig().getString("lang2"));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            sender.sendMessage("ok");
            return true;
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            getConfig().set(args[1], args[2]);
            sender.sendMessage("ok");
            saveConfig();
            return true;
        }
        sender.sendMessage("命令/worldblockrule set 世界 类型(place/break)");
        return true;
    }
}
