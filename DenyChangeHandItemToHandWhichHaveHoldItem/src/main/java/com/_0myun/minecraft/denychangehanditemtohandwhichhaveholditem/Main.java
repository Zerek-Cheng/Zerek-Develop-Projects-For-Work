package com._0myun.minecraft.denychangehanditemtohandwhichhaveholditem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        new Souter().runTaskLater(this, 1);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        reloadConfig();
        sender.sendMessage("reload ok");
        return true;
    }

    @EventHandler
    public void onChange(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();
        int oldSlot = e.getPreviousSlot();
        int newSlot = e.getNewSlot();
        if (oldSlot == newSlot) return;
        ItemStack oldItem = inv.getItem(oldSlot);
        ItemStack newItem = inv.getItem(newSlot);
        if (newItem == null || newItem.getType().equals(Material.AIR)) return;
        if (inConfig(oldItem) && inConfig(newItem)) {//A不能互相切换
            e.setCancelled(true);
            p.updateInventory();
            p.sendMessage(getConfig().getString("lang1"));
            return;
        }

        if ((inConfig(oldItem) && inConfigB(newItem)) || inConfigB(oldItem) && inConfig(newItem)) {//AB不能互相切换
            e.setCancelled(true);
            p.updateInventory();
            p.sendMessage(getConfig().getString("lang1"));
            return;
        }
    }

    public boolean inConfig(ItemStack item) {
        if (item == null || item.getType().equals(Material.AIR)) return false;
        List<String> ids = getConfig().getStringList("ids");
        if (ids.contains(item.getTypeId() + ":" + item.getDurability())) return true;
        if (ids.contains(item.getTypeId() + ":*")) return true;
        if (ids.contains(item.getTypeId())) return true;
        return false;
    }

    public boolean inConfigB(ItemStack item) {
        if (item == null || item.getType().equals(Material.AIR)) return false;
        List<String> ids = getConfig().getStringList("idsb");
        if (ids.contains(item.getTypeId() + ":" + item.getDurability())) return true;
        if (ids.contains(item.getTypeId() + ":*")) return true;
        if (ids.contains(item.getTypeId())) return true;
        return false;
    }
}
