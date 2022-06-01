package com._0myun.minecraft.worldreward;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerListener implements Listener {
    @EventHandler
    public void onBreakBox(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();
        if (block == null || block.getType().equals(Material.AIR)) return;
        if (!Main.INSTANCE.isBox(block.getLocation())) return;
        if (!p.isOp()) {
            p.sendMessage(Main.INSTANCE.getLang("lang3"));
            return;
        }
        p.sendMessage(Main.INSTANCE.getLang("lang4"));
        Main.INSTANCE.removeBox(block.getLocation());
    }

    @EventHandler
    public void onRight(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block block = e.getClickedBlock();
        if (block == null || block.getType().equals(Material.AIR)) ;
        if (!Main.INSTANCE.isBox(block.getLocation())) return;
        e.setCancelled(true);
        Inventory inv = Main.INSTANCE.getRewards().get(e.getPlayer().getWorld().getName());
        int itemAmount[] = {0};
        inv.forEach(itemStack -> {
            if (itemStack != null && !itemStack.getType().equals(Material.AIR)) itemAmount[0]++;
        });
        if (itemAmount[0]<1){
            e.getPlayer().sendMessage(Main.INSTANCE.getLang("lang8"));
            return;
        }
        e.getPlayer().openInventory(inv);
        e.getPlayer().sendMessage(Main.INSTANCE.getLang("lang7"));
    }
}
