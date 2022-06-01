package com._0myun.minecraft.removeeenchantfromarmor;

import org.bukkit.Bukkit;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerChecker extends BukkitRunnable {
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            EntityEquipment eq = p.getEquipment();
            boolean has = false;
            has = !has ? Main.INSTANCE.checkAndRemove(eq.getHelmet()) : true;
            has = !has ? Main.INSTANCE.checkAndRemove(eq.getBoots()) : true;
            has = !has ? Main.INSTANCE.checkAndRemove(eq.getChestplate()) : true;
            has = !has ? Main.INSTANCE.checkAndRemove(eq.getLeggings()) : true;
            if (has) p.sendMessage(Main.INSTANCE.getConfig().getString("lang1"));
        });
    }
}
