package com._0myun.minecraft.tiredcommands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PhysicalManager.refresh(p.getUniqueId());
    }

    @EventHandler
    public void onClickRight(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack itemInHand = p.getItemInHand();
        String name = PotionManager.getNameFromItemStack(itemInHand);
        if (name == null || name.isEmpty()) return;
        e.setCancelled(true);
        int add = PotionManager.getPotionAdd(name);
        PhysicalManager.takePhysical(p.getUniqueId(), -add);
        itemInHand.setAmount(itemInHand.getAmount() - 1);
        p.sendMessage(LangManager.get("lang6"));
    }
}
