package com._0myun.minecraft.chargecolorchat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.reloadConfig();
        sender.sendMessage("reloadok");
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        if (!message.contains("§") && !message.contains("&")) return;
        PlayerInventory inv = p.getInventory();
        int[] first = {-1};
        inv.all(Material.INK_SACK).forEach((raw, itemTmp) -> {
            if (itemTmp.getData().getData() != 4) return;
            first[0] = raw;
        });
        ItemStack item = null;
        if (first[0] != -1) {
            item = inv.getItem(first[0]);
        }
        if (first[0] == -1 || item.getAmount() < getConfig().getInt("amount")) {
            e.setCancelled(true);
            p.sendMessage(getConfig().getString("lang1"));
            return;
        }
        item.setAmount(item.getAmount() - getConfig().getInt("amount"));
        p.sendMessage(String.format(getConfig().getString("lang2"), String.valueOf(getConfig().getInt("amount"))));
    }
}
