package com._0myun.minecraft.bindandunband;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerInvChecker extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerInventory inv = p.getInventory();
            inv.forEach(item -> {
                if (!Main.plugin.isBind(item)) return;//没有绑定
                if (p.getUniqueId().toString().equalsIgnoreCase(Main.plugin.getBinder(item))
                        || Main.plugin.getBinder(item).equalsIgnoreCase(p.getUniqueId().toString())) return;//绑定人是自己
                if (!Main.plugin.getOwner(item).isEmpty() && !Main.plugin.getOwner(item).equalsIgnoreCase(p.getUniqueId().toString())) {//不为空且不是自己
                    p.sendMessage(Main.plugin.getConfig().getString("lang6"));
                    p.getLocation().getWorld().dropItem(p.getLocation(), item);
                    inv.remove(item);
                    p.getCanPickupItems();
                    return;
                }
            });
        }
    }
}
