package com._0myun.bind;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static com._0myun.bind.Utils.*;

public class InvCheckTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            try {
                for (ItemStack itemStack : p.getInventory()) {
                    if (itemStack == null || itemStack.getType().equals(Material.AIR)) continue;

                    if (R.INSTANCE.checkMeta(itemStack, true) && Utils.getOwner(itemStack) == null)
                        setOwner(itemStack, p.getUniqueId());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                for (ItemStack itemStack : p.getInventory()) {
                    if (itemStack == null || itemStack.getType().equals(Material.AIR) || !hasBind(itemStack)) continue;
                    String owner = getOwner(itemStack);
                    if (p.getUniqueId().toString().equalsIgnoreCase(owner)) continue;
                    p.getInventory().remove(itemStack);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
