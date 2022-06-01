package com._0myun.minecraft.debugpokelucky;

import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Listener implements org.bukkit.event.Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        ItemStack itemInHand = p.getItemInHand();
        int level = itemInHand.getEnchantmentLevel(Enchantment.SILK_TOUCH);
        if (level == 0) return;
        if (!b.getType().toString().equalsIgnoreCase("POKELUCKY_POKE_LUCKY"))return;
        e.setCancelled(true);
        e.setDropItems(false);
    }
}
