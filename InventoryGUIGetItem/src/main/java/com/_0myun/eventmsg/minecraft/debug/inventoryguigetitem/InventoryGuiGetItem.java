package com._0myun.eventmsg.minecraft.debug.inventoryguigetitem;

import org.bukkit.Bukkit;
import org.bukkit.Warning;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class InventoryGuiGetItem extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.reloadConfig();
        sender.sendMessage("reload ok！");
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(InventoryClickEvent e) {
        InventoryView view = e.getView();
        if (view == null) return;
        Inventory topInventory = view.getTopInventory();
        if (topInventory == null) return;
        if (!this.getConfig().getStringList("black").contains(e.getInventory().getTitle())) return;
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onIgClick(InventoryClickEvent e) {
        if (this.getConfig().getBoolean("debug") && e.getWhoClicked().isOp()) {
            Bukkit.getLogger().log(Level.WARNING,"名字在这里【" + e.getInventory().getTitle() + "】");
        }

    }
}
